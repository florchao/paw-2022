package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.Contact;
import ar.edu.itba.paw.model.Employee;
import ar.edu.itba.paw.model.Employer;
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
    public Optional<List<Contact>> getAllContacts(Employee userId,  Long page, int pageSize) {
        final TypedQuery<Contact> query = em.createQuery("select u from Contact u where u.employeeID =:userId", Contact.class).setFirstResult((int) (page * pageSize)).setMaxResults(pageSize);
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
    public int getPageNumber(long id, int pageSize) {
        Employee employee = em.find(Employee.class, id);
        TypedQuery<Long> filteredQuery = em.createQuery("SELECT count(c) FROM Contact c WHERE c.employeeID =:employee ", Long.class);
        filteredQuery.setParameter("employee", employee);
        return (int) Math.ceil( (double) filteredQuery.getSingleResult() / pageSize);
    }

    @Override
    public Boolean existsContact(long employeeId, long employerId) {
        Employer employer = em.find(Employer.class, employerId);
        Employee employee = em.find(Employee.class, employeeId);
        TypedQuery<Contact> contactTypedQuery = em.createQuery("SELECT c FROM Contact c WHERE c.employerID =:employer AND c.employeeID =:employee", Contact.class);
        contactTypedQuery.setParameter("employer", employer);
        contactTypedQuery.setParameter("employee", employee);
        return !contactTypedQuery.getResultList().isEmpty();
    }
}
