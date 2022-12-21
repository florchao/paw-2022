package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.*;
import ar.edu.itba.paw.model.exception.AlreadyExistsException;
import ar.edu.itba.paw.model.exception.UserNotFoundException;
import ar.edu.itba.paw.persistence.ContactDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;

@Service
public class ContactServiceImpl implements ContactService {

    @Autowired
    private ContactDao contactDao;
    @Autowired
    private MailingService mailingService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private EmployerService employerService;


    @Transactional(readOnly = true)
    @Override
    public List<Contact> getAllContacts(long id, Long page, int pageSize) throws UserNotFoundException {
        Employee employee = employeeService.getEmployeeById(id);
        return contactDao.getAllContacts(employee, page, pageSize);
    }


    @Override
    public int getPageNumber(long id, int pageSize) {
        return contactDao.getPageNumber(id, pageSize);
    }


    @Transactional
    @Override
    public boolean create(long employeeId, long employerId, Date created, String contactMessage, String phoneNumber) throws UserNotFoundException, AlreadyExistsException {
        Employee employee = employeeService.getEmployeeById(employeeId);
        Employer employer = employerService.getEmployerById(employerId);
        boolean exists;
        exists = !contactDao.existsContact(employee, employer).isEmpty();
        if (exists) {
            return true;
        }
        contactDao.create(employee, employer, created, contactMessage, phoneNumber);
        return false;
    }

    @Transactional
    @Override
    public boolean contact(User to, User from, String message, String name, String phoneNumber) throws UserNotFoundException, AlreadyExistsException {
        if (create(to.getId(), from.getId(), new Date(System.currentTimeMillis()), message, phoneNumber))
            return true;
        mailingService.sendContactMail(from.getEmail(), to.getEmail(), name, LocaleContextHolder.getLocale());
        return false;
    }

    @Transactional
    @Override
    public void contactUS(String message, String from, String name) {
        String lang = LocaleContextHolder.getLocale().getLanguage();
        mailingService.sendContactUsMail(name, from, message, LocaleContextHolder.getLocale());
    }

    @Transactional
    @Override
    public void changedStatus(int status, Job job, Employee employee) {
        String titile = job.firstWordsToUpper();
        mailingService.sendChangeStatus(status, employee.getId().getEmail(), job.getEmployerId().getId().getEmail(), titile, LocaleContextHolder.getLocale());
    }

    @Override
    public List<Contact> existsContact(long employeeId, long employerId) throws UserNotFoundException {
        Employee employee = employeeService.getEmployeeById(employeeId);
        Employer employer = employerService.getEmployerById(employerId);
        return contactDao.existsContact(employee, employer);
    }


}
