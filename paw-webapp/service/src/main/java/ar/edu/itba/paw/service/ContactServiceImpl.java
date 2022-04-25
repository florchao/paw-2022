package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Contact;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.persistence.ContactDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
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
    public Optional<List<Contact>> getAllContacts() {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> optional = userService.findByUsername(principal.getUsername());
        User user = optional.get();
        return contactDao.getAllContacts(user.getId());
    }

    @Override
    public Contact create(long employeeId, long employerId, Date created, String contactMessage, String phoneNumber) {
        return contactDao.create(employeeId, employerId, created, contactMessage, phoneNumber);
    }

    @Override
    public void contact(User to, String message, String name, String phoneNumber) {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> optional = userService.findByUsername(principal.getUsername());
        if(optional.isPresent()) {
            User from = optional.get();
            create(to.getId(), from.getId(), new Date(System.currentTimeMillis()), message, phoneNumber);
            mailingService.sendMail(from.getUsername(), to.getUsername(), name);
        }
        //mailingService.sendMail(replyTo, to, name, message);

    }
}
