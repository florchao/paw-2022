package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.persistence.UserDao;
import ar.edu.itba.paw.service.UserServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

@RunWith(MockitoJUnitRunner.Silent.class)
public class UserServiceImplTest {
    private static final String PASSWORD = "passwordpassword";
    private static final String USERNAME = "username";
    private static final long ID = 1;

    @Mock
    private UserDao mockDao;

    @InjectMocks
    private UserServiceImpl userService = new UserServiceImpl();

    @Test
    public void testCreate(){

        Mockito.when(mockDao.create(Mockito.eq(USERNAME)))
                .thenReturn(new User(1, USERNAME));

        Optional<User> maybeUser = Optional.ofNullable(userService.create(USERNAME));

        Assert.assertNotNull(maybeUser);
        Assert.assertTrue(maybeUser.isPresent());
        Assert.assertEquals(USERNAME, maybeUser.get().getUsername());
        //Assert.assertEquals(PASSWORD, maybeUser.get().getPassword());
    }

    @Test
    public void testCreateEmptyPassword(){


        Optional<User> maybeUser = Optional.ofNullable(userService.create(USERNAME));

        Assert.assertNotNull(maybeUser);
        Assert.assertFalse(maybeUser.isPresent());
    }

    @Test
    public void testCreateAlreadyExists(){

        Mockito.when(mockDao.getUserById(Mockito.eq(ID)))
                .thenReturn(Optional.of(new User(1, USERNAME)));

        Optional<User> maybeUser = Optional.ofNullable(userService.create(USERNAME));

        Assert.assertNotNull(maybeUser);
        Assert.assertFalse(maybeUser.isPresent());
    }

    @Test
    public  void testGetUserById(){

        Mockito.when(mockDao.create(Mockito.eq(USERNAME)))
                .thenReturn(new User(1, USERNAME));

        Optional<User> maybeUser = userService.getUserById(1);

        Assert.assertNotNull(maybeUser);
        Assert.assertFalse(maybeUser.isPresent());
    }

}
