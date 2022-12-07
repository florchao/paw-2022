package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Contact;
import ar.edu.itba.paw.model.Employee;
import ar.edu.itba.paw.model.Job;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.exception.AlreadyExistsException;
import ar.edu.itba.paw.model.exception.UserNotFoundException;

import java.sql.Date;
import java.util.List;

public interface ContactService {

    List<Contact> getAllContacts(long id, Long page, int pageSize) throws UserNotFoundException;

    int getPageNumber(long id, int pageSize);

    boolean create(long employeeId, long employerId, Date created, String contactMessage, String phoneNumber) throws AlreadyExistsException, UserNotFoundException;

    boolean contact(User to, User from, String message, String name, String phoneNumber) throws UserNotFoundException, AlreadyExistsException;

    void contactUS(String message, String from, String name);

    void changedStatus(int status, Job job, Employee employee);

    List<Contact> existsContact(long employeeId, long employerId) throws UserNotFoundException;
}

