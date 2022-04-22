package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.Contact;
import ar.edu.itba.paw.model.Experience;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class ContactJdbcDao implements ContactDao{
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    private static final RowMapper<Contact> CONTACT_ROW_MAPPER = (rs, rowNum) ->
            new Contact(rs.getLong("employeeid"), rs.getLong("employerid"), rs.getString("message"), rs.getString("phone"), rs.getDate("created"));

    private static final RowMapper<Contact> CONTACT_NAME_ROW_MAPPER = (rs, rowNum) ->
            new Contact(rs.getLong("employeeid"), rs.getString("email"), rs.getString("message"), rs.getString("phone"), rs.getDate("created"));

    @Autowired
    public ContactJdbcDao(final DataSource ds){
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcInsert = new SimpleJdbcInsert(ds)
                .withTableName("Contact");
    }

    @Override
    public Optional<List<Contact>> getAllContacts(long id) {
        List<Contact> query = jdbcTemplate.query("SELECT employeeid, email, message, phone, created FROM contact JOIN users ON employerId=userId WHERE employeeID = ?", new Object[] {id}, CONTACT_NAME_ROW_MAPPER);
        return Optional.of(query);
    }

    @Override
    public Contact create(long employeeId, long employerId, Date created, String contactMessage, String phoneNumber) {
        final Map<String, Object> contactData = new HashMap<>();
        contactData.put("employeeid", employeeId);
        contactData.put("employerid", employerId);
        contactData.put("created", created);
        contactData.put("message", contactMessage);
        contactData.put("phone", phoneNumber);

        jdbcInsert.execute(contactData);
        return new Contact(employeeId, employerId, contactMessage, phoneNumber, created);
    }
}
