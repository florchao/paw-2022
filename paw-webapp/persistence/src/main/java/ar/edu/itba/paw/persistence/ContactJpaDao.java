package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.Contact;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Repository
public class ContactJpaDao implements ContactDao{
    @PersistenceContext
    private EntityManager em;

    @Override
    public Optional<List<Contact>> getAllContacts(long userId) {
        return Optional.empty();
    }

    @Override
    public Contact create(long employeeId, long employerId, Date created, String contactMessage, String phoneNumber) {
        return null;
    }

    @Override
    public Optional<Boolean> existsContact(long employeeId, long employerId) {
        return Optional.empty();
    }
}
