package ar.edu.itba.paw.persistence;


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
import java.util.List;
import java.util.Optional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ar.edu.itba.paw.persistence.TestConfig.class)
@Transactional
@Sql("classpath:schema.sql")
@Rollback
public class UserJpaDaoTest {
    private static final String PASSWORD = "Password";
    private static final String USERNAME = "Username";
    private static final int ROLE = 1;

    @PersistenceContext
    private EntityManager em;
    @Autowired
    DataSource dataSource;

    @Autowired
    private UserJpaDao userJpaDao;

    @Test
    public void testCreate(){
        final User user = userJpaDao.create(USERNAME, PASSWORD, ROLE);
        em.flush();

        Assert.assertEquals(USERNAME, user.getEmail());
        Assert.assertEquals(PASSWORD, user.getPassword());
    }

    @Test
    public void testGetById(){
        em.createNativeQuery("INSERT INTO users VALUES (?,?,?,?)")
                .setParameter(1,0)
                .setParameter(2, USERNAME)
                .setParameter(3, PASSWORD)
                .setParameter(4, ROLE)
                .executeUpdate();

        final Optional<User> user = userJpaDao.getUserById(0);

        Assert.assertNotNull(user);
        Assert.assertTrue(user.isPresent());
        Assert.assertEquals(USERNAME, user.get().getEmail());
        Assert.assertEquals(PASSWORD, user.get().getPassword());
        Assert.assertEquals(ROLE, user.get().getRole());

    }

    @Test
    public void testGetByUsername(){
        em.createNativeQuery("INSERT INTO users VALUES (?,?,?,?)")
                .setParameter(1,0)
                .setParameter(2, USERNAME)
                .setParameter(3, PASSWORD)
                .setParameter(4, ROLE)
                .executeUpdate();

        final Optional<User> user = userJpaDao.getUserByUsername("Username");

        Assert.assertNotNull(user);
        Assert.assertTrue(user.isPresent());
        Assert.assertEquals(USERNAME, user.get().getEmail());
        Assert.assertEquals(PASSWORD, user.get().getPassword());
        Assert.assertEquals(ROLE, user.get().getRole());

    }

    @Test
    public void testUpdate(){
        em.createNativeQuery("INSERT INTO users VALUES (?,?,?,?)")
                .setParameter(1,0)
                .setParameter(2, USERNAME)
                .setParameter(3, PASSWORD)
                .setParameter(4, ROLE)
                .executeUpdate();


        userJpaDao.update("Username", "Password2");
        final Optional<User> user = userJpaDao.getUserById(0);

        Assert.assertNotNull(user);
        Assert.assertTrue(user.isPresent());
        Assert.assertEquals(USERNAME, user.get().getEmail());
        Assert.assertEquals("Password2", user.get().getPassword());
        Assert.assertEquals(ROLE, user.get().getRole());

    }

    @Test
    public void testGetAll(){
        em.createNativeQuery("INSERT INTO users VALUES (?,?,?,?)")
                .setParameter(1,0)
                .setParameter(2, USERNAME)
                .setParameter(3, PASSWORD)
                .setParameter(4, ROLE)
                .executeUpdate();

        final List<User> list = userJpaDao.getAll(1);

        Assert.assertFalse(list.isEmpty());
        Assert.assertEquals(1, list.size());
    }

    @Test
    public void testDelete(){
        em.createNativeQuery("INSERT INTO users VALUES (?,?,?,?)")
                .setParameter(1,0)
                .setParameter(2, USERNAME)
                .setParameter(3, PASSWORD)
                .setParameter(4, ROLE)
                .executeUpdate();

        userJpaDao.deleteUser(0);
        Optional<User> user = userJpaDao.getUserById(0);

        Assert.assertFalse(user.isPresent());
    }



}