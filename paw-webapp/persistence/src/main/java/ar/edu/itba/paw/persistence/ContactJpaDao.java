package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.Contact;
import ar.edu.itba.paw.model.Employee;
import ar.edu.itba.paw.model.Employer;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.sql.Date;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class ContactJpaDao implements ContactDao{
    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Contact> getAllContacts(Employee userId,  Long page, int pageSize) {

        final Query idQuery = em.createNativeQuery("SELECT employerid FROM contact where employeeid =:userid LIMIT :pageSize OFFSET :offset");
        idQuery.setParameter("userid", userId);
        idQuery.setParameter("pageSize", pageSize);
        idQuery.setParameter("offset", page * pageSize);
        @SuppressWarnings("unchecked")
        List<Long> ids = (List<Long>) idQuery.getResultList().stream().map(o -> ((Integer) o).longValue()).collect(Collectors.toList());

        if (ids.isEmpty()) {
            return Collections.emptyList();
        }

        // noinspection JpaQlInspection
        final TypedQuery<Contact> query = em.createQuery("select u from Contact u where u.employeeID =:userId and employerid in :ids", Contact.class);
        query.setParameter("ids", ids);
        query.setParameter("userId", userId);
        return query.getResultList();
    }

    @Override
    public Contact create(Employee employeeId, Employer employerId, Date created, String contactMessage, String phoneNumber) {
        final Contact contact = new Contact(employeeId, employerId, contactMessage, phoneNumber, created);
        em.persist(contact);
        return contact;
    }

    @Override
    public int getPageNumber(long id, int pageSize) {
        Employee employee = em.find(Employee.class, id);
        TypedQuery<Long> filteredQuery = em.createQuery("SELECT count(c) FROM Contact c WHERE c.employeeID =:employee ", Long.class);
        filteredQuery.setParameter("employee", employee);
        return (int) Math.ceil( (double) filteredQuery.getSingleResult() / pageSize);
    }

    @Override
    public List<Contact> existsContact(Employee employeeId, Employer employerId) {
        TypedQuery<Contact> contactTypedQuery = em.createQuery("SELECT c FROM Contact c WHERE c.employerID =:employer AND c.employeeID =:employee", Contact.class);
        contactTypedQuery.setParameter("employer", employerId);
        contactTypedQuery.setParameter("employee", employeeId);
        return contactTypedQuery.getResultList();
    }
}
