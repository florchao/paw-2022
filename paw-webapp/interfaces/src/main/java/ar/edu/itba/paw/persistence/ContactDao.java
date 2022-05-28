package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.Contact;
import ar.edu.itba.paw.model.Employee;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

public interface ContactDao {

    Optional<List<Contact>> getAllContacts(Employee userId);

    Contact create(long employeeId, long employerId, Date created, String contactMessage, String phoneNumber);

    Optional<Boolean> existsContact(long employeeId, long employerId);
}
