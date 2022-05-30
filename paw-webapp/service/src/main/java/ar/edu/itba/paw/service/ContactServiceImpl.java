package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Contact;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.exception.AlreadyExistsException;
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


    @Override
    public Optional<List<Contact>> getAllContacts(long id) {
        return contactDao.getAllContacts(id);
    }

    @Transactional
    @Override
    public Contact create(long employeeId, long employerId, Date created, String contactMessage, String phoneNumber) {
        Optional<Boolean> exists = contactDao.existsContact(employeeId, employerId);
        if(exists.isPresent() && exists.get()){
            throw new AlreadyExistsException("You already have a contact with this employee");
        }
        return contactDao.create(employeeId, employerId, created, contactMessage, phoneNumber);
    }

    @Override
    public void contact(User to, String message, String name, String phoneNumber) {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> optional = userService.findByUsername(principal.getUsername());
        if(optional.isPresent()) {
            User from = optional.get();
            create(to.getId(), from.getId(), new Date(System.currentTimeMillis()), message, phoneNumber);
            mailingService.sendContactMail(from.getUsername(), to.getUsername(), name);
        }

    }

    @Override
    public void contactUS(String message, String from, String name) {
        mailingService.sendContactUsMail(name, from, message);
    }

    @Override
    public Optional<Boolean> existsContact(long employeeId, long employerId) {
        return contactDao.existsContact(employeeId, employerId);
    }


}
