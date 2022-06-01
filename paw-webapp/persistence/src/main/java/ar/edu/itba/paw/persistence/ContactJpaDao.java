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

    //todo hay que actualizarla
    @Override
    //todo update
    public Optional<List<Contact>> getAllContacts(Employee userId,  Long page, int pageSize) {
        //List<Contact> query = jdbcTemplate.query("SELECT employeeid, name, email, message, phone, created, contact.employerId FROM contact JOIN users ON employerId=userId JOIN employer ON contact.employerID = employer.employerID WHERE employeeID = ? ORDER BY created DESC LIMIT ? OFFSET ?", new Object[] {id, pageSize, page*pageSize}, CONTACT_NAME_ROW_MAPPER);
        //        return Optional.of(query);
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

    //todo falta implementarla
    @Override
    public int getPageNumber(long id, int pageSize) {
//        String query = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM contact JOIN users ON employerId=userId JOIN employer ON contact.employerID = employer.employerID WHERE employeeID = ?", new Object[] {id}, String.class);
//        return (int) Math.ceil((float) Integer.parseInt(query) / pageSize);
        Employee employee = em.find(Employee.class, id);
        StringBuilder queryToBuild = new StringBuilder();
        TypedQuery<Long> filteredQuery = em.createQuery("SELECT count(c) FROM Contact c WHERE c.employeeID =:employee ", Long.class);
        filteredQuery.setParameter("employee", employee);
        return (int) Math.ceil( (double) filteredQuery.getSingleResult() / pageSize);
//        return 0;
    }

    @Override
    public Optional<Boolean> existsContact(long employeeId, long employerId) {
        Employer employer = em.find(Employer.class, employerId);
        Employee employee = em.find(Employee.class, employeeId);
        TypedQuery<Contact> contactTypedQuery = em.createQuery("SELECT c FROM Contact c WHERE c.employerID =:employer AND c.employeeID =:employee", Contact.class);
        contactTypedQuery.setParameter("employer", employer);
        contactTypedQuery.setParameter("employee", employee);
        return Optional.of(!contactTypedQuery.getResultList().isEmpty());
    }
}
