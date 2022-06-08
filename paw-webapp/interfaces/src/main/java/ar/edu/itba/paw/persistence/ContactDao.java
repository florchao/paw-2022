package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.Contact;
import ar.edu.itba.paw.model.Employee;
import ar.edu.itba.paw.model.Employer;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

public interface ContactDao {

    int getPageNumber(long id, int pageSize);

    List<Contact> getAllContacts(Employee userId,Long page, int pageSize);

    Contact create(Employee employeeId, Employer employerId, Date created, String contactMessage, String phoneNumber);

    Boolean existsContact(Employee employeeId, Employer employerId);
}
