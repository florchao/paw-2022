package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Contact;
import ar.edu.itba.paw.model.Employee;
import ar.edu.itba.paw.model.Job;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.exception.AlreadyExistsException;
import ar.edu.itba.paw.model.exception.UserNotFoundException;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

public interface ContactService {

    List<Contact> getAllContacts(long id, Long page, int pageSize) throws UserNotFoundException;

    int getPageNumber(long id, int pageSize);

    Contact create(long employeeId, long employerId, Date created, String contactMessage, String phoneNumber) throws AlreadyExistsException, UserNotFoundException;

    void contact(User to, String message, String name, String phoneNumber) throws UserNotFoundException, AlreadyExistsException;

    void contactUS(String message, String from, String name);

    void changedStatus(int status, Job job, Employee employee);

    Boolean existsContact(long employeeId, long employerId) throws UserNotFoundException;
}

