package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.exception.PassMatchException;
import ar.edu.itba.paw.model.exception.UserFoundException;
import ar.edu.itba.paw.persistence.UserDao;
import org.omg.CORBA.UserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserDao userDao;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public Optional<User> getUserById(long id) {
        return userDao.getUserById(id);
    }

    @Override
    public User create(String username, String password, String confPassword) throws UserFoundException, PassMatchException {
        // TODO: validate username / password
        // TODO: send email validation mail
        // TODO: ...
        if (findByUsername(username) != null) {
            throw new UserFoundException("There is an account with that email address: "
                    + username);
        }
        if(!password.equals(confPassword)){
            throw new PassMatchException("Passwords don't match");
        }
        return userDao.create(username, passwordEncoder.encode(password));
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userDao.getUserByUsername(username);
    }

    @Override
    public boolean update(String username, String password) {
        return userDao.update(username, password);
    }
}
