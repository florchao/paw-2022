package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.*;
import ar.edu.itba.paw.model.exception.AlreadyExistsException;
import ar.edu.itba.paw.model.exception.UserNotFoundException;
import ar.edu.itba.paw.persistence.ContactDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ContactServiceImpl implements ContactService{

    @Autowired
    private ContactDao contactDao;
    @Autowired
    private MailingService mailingService;
    @Autowired
    private UserService userService;
    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private EmployerService employerService;


    @Transactional(readOnly = true)
    @Override
    public List<Contact> getAllContacts(long id, Long page, int pageSize) throws UserNotFoundException {
        Optional<Employee> employee = employeeService.getEmployeeById(id);
        return employee.map(value -> contactDao.getAllContacts(value, page, pageSize)).orElse(null);
    }


    @Override
    public int getPageNumber(long id, int pageSize) {
        return contactDao.getPageNumber(id, pageSize);
    }


    @Transactional
    @Override
    public Contact create(long employeeId, long employerId, Date created, String contactMessage, String phoneNumber) throws UserNotFoundException, AlreadyExistsException {
        Optional<Employee> employee = employeeService.getEmployeeById(employeeId);
        Optional<Employer> employer = employerService.getEmployerById(employerId);
        Boolean exists = false;
        if(employee.isPresent() && employer.isPresent())
            exists = contactDao.existsContact(employee.get(), employer.get());
        if(exists){
            throw new AlreadyExistsException("You already have a contact with this employee");
        }
        return contactDao.create(employee.get(), employer.get(), created, contactMessage, phoneNumber);
    }

    @Transactional
    @Override
    public void contact(User to, String message, String name, String phoneNumber) throws UserNotFoundException, AlreadyExistsException {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> optional = userService.findByUsername(principal.getUsername());
        if(optional.isPresent()) {
            User from = optional.get();
            create(to.getId(), from.getId(), new Date(System.currentTimeMillis()), message, phoneNumber);
            mailingService.sendContactMail(from.getEmail(), to.getEmail(), name);
        }
    }

    @Transactional
    @Override
    public void contactUS(String message, String from, String name) {
        mailingService.sendContactUsMail(name, from, message);
    }

    @Transactional
    @Override
    public void changedStatus(int status, Job job, Employee employee) {
        job.firstWordsToUpper();
        mailingService.sendChangeStatus(status, employee.getId().getEmail(), job.getEmployerId().getId().getEmail(), job.getTitle());
    }

    @Override
    public Boolean existsContact(long employeeId, long employerId) throws UserNotFoundException {
        Optional<Employee> employee = employeeService.getEmployeeById(employeeId);
        Optional<Employer> employer = employerService.getEmployerById(employerId);
        if(employee.isPresent() && employer.isPresent())
            return contactDao.existsContact(employee.get(), employer.get());
        return false;
    }


}
