package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.*;
import ar.edu.itba.paw.model.exception.AlreadyExistsException;
import ar.edu.itba.paw.model.exception.UserNotFoundException;
import ar.edu.itba.paw.persistence.ContactDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.Collections;
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
    public boolean create(long employeeId, long employerId, Date created, String contactMessage, String phoneNumber) throws UserNotFoundException, AlreadyExistsException {
        Optional<Employee> employee = employeeService.getEmployeeById(employeeId);
        Optional<Employer> employer = employerService.getEmployerById(employerId);
        boolean exists;
        if(employee.isPresent() && employer.isPresent()) {
            exists = !contactDao.existsContact(employee.get(), employer.get()).isEmpty();
            if (exists) {
                return true;
            }
            contactDao.create(employee.get(), employer.get(), created, contactMessage, phoneNumber);
        }
        return false;
    }

    @Transactional
    @Override
    public boolean contact(User to, User from, String message, String name, String phoneNumber) throws UserNotFoundException, AlreadyExistsException {
//        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (create(to.getId(), from.getId(), new Date(System.currentTimeMillis()), message, phoneNumber))
            return true;
        mailingService.sendContactMail(from.getEmail(), to.getEmail(), name);
        return false;
    }

    @Transactional
    @Override
    public void contactUS(String message, String from, String name) {
        mailingService.sendContactUsMail(name, from, message);
    }

    @Transactional
    @Override
    public void changedStatus(int status, Job job, Employee employee) {
        String titile = job.firstWordsToUpper();
        mailingService.sendChangeStatus(status, employee.getId().getEmail(), job.getEmployerId().getId().getEmail(), titile);
    }

    @Override
    public List<Contact> existsContact(long employeeId, long employerId) throws UserNotFoundException {
        Optional<Employee> employee = employeeService.getEmployeeById(employeeId);
        Optional<Employer> employer = employerService.getEmployerById(employerId);
        if(employee.isPresent() && employer.isPresent())
            return contactDao.existsContact(employee.get(), employer.get());
        return Collections.emptyList();
    }


}
