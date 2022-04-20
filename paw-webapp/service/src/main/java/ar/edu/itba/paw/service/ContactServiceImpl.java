package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Contact;
import ar.edu.itba.paw.persistence.ContactDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ContactServiceImpl implements ContactService{

    @Autowired
    private ContactDao experienceDao;

    @Autowired
    private MailingService mailingService;

    @Override
    public Optional<List<Contact>> getAllContacts(long userId) {
        return experienceDao.getAllContacts(userId);
    }

    @Override
    public Contact create(long employeeId, long employerId, Date created, String contactMessage) {
        return experienceDao.create(employeeId, employerId, created, contactMessage);
    }

    @Override
    public void contact(String replyTo, String to, String name, String message) {
        mailingService.sendMail(replyTo, to, name, message);
    }
}
