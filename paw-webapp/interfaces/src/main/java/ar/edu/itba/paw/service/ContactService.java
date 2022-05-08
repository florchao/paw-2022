package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Contact;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.exception.AlreadyExistsException;


import java.sql.Date;
import java.util.List;
import java.util.Optional;

public interface ContactService {

    Optional<List<Contact>> getAllContacts(long id);

    Contact create(long employeeId, long employerId, Date created, String contactMessage, String phoneNumber) throws AlreadyExistsException;

    void contact(User to, String message, String name, String phoneNumber);

    void contactUS(String message, String from, String name);
}

