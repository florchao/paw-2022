package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.*;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Repository
public class ContactJpaDao implements ContactDao{
    @PersistenceContext
    private EntityManager em;

    @Override
    public Optional<List<Contact>> getAllContacts(long userId) {
        final TypedQuery<Contact> query = em.createQuery("select u from Contact u where u.employerID =:userId", Contact.class);
        query.setParameter("userId", userId);
        return Optional.ofNullable(query.getResultList());
    }

    @Override
    public Contact create(long employeeId, long employerId, Date created, String contactMessage, String phoneNumber) {
        Optional<Employee> employee=  Optional.ofNullable(em.find(Employee.class, employeeId));
        Optional<Employer> employer=  Optional.ofNullable(em.find(Employer.class, employerId));
        if(employee.isPresent() && employer.isPresent()) {
            final Contact contact = new Contact(employee.get(), employer.get(), contactMessage, phoneNumber, created);
            em.persist(contact);
            return contact;

        }
        return null;
    }

    @Override
    public Optional<Boolean> existsContact(long employeeId, long employerId) {
        TypedQuery<Contact> contactTypedQuery = em.createQuery("SELECT c FROM Contact c WHERE c.employerID = :employer AND c.employeeID = :employee", Contact.class);
        contactTypedQuery.setParameter("employer", employerId);
        contactTypedQuery.setParameter("employee", employeeId);
        return Optional.of(!contactTypedQuery.getResultList().isEmpty());
    }
}
