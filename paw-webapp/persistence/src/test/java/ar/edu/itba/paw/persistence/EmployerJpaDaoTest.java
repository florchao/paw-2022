package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.Employer;
import ar.edu.itba.paw.model.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import java.util.Optional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ar.edu.itba.paw.persistence.TestConfig.class)
@Sql("classpath:schema.sql")
@Transactional
@Rollback
public class EmployerJpaDaoTest {

    @Autowired
    public DataSource dataSource;

    @PersistenceContext
    private EntityManager em;

    private static final String PASSWORD = "Password";
    private static final String USERNAME = "Username";
    private static final int ROLE = 2;

    @Autowired
    private EmployerJpaDao employerJdbcDao;

    @Autowired
    private UserJpaDao userJpaDao;

    private static final String NAME = "name";
    private static final long ID = 0;
    private static final byte[] IMAGE = null;

    @Test
    public void testCreate() {
        em.createNativeQuery("INSERT INTO users VALUES (?,?,?,?)")
                .setParameter(1, 0)
                .setParameter(2, USERNAME)
                .setParameter(3, PASSWORD)
                .setParameter(4, ROLE)
                .executeUpdate();
        byte[] image = {};
        Optional<User> user = userJpaDao.getUserById(0);
        final Employer employer = employerJdbcDao.create(NAME, user.get(), image);

        Assert.assertEquals(ID, employer.getId().getId());
        Assert.assertEquals(NAME, employer.getName());

    }

    @Test
    public void testGetEmployeeById() {
        em.createNativeQuery("INSERT INTO users VALUES (?,?,?,?)")
                .setParameter(1, 0)
                .setParameter(2, USERNAME)
                .setParameter(3, PASSWORD)
                .setParameter(4, ROLE)
                .executeUpdate();
        Optional<User> user = userJpaDao.getUserById(0);
        byte[] image = {};
        employerJdbcDao.create(NAME, user.get(), image);


        Optional<Employer> employer = employerJdbcDao.getEmployerById(0);
        Assert.assertNotNull(employer);
        Assert.assertTrue(employer.isPresent());
        Assert.assertEquals(NAME, employer.get().getName());

    }
}



