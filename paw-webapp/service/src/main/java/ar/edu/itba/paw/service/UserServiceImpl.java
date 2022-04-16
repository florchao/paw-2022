package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.persistence.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserDao userDao;


    @Override
    public Optional<User> getUserById(long id) {
        return userDao.getUserById(id);
    }

    @Override
    public User create(String username, String password) {
        // TODO: validate username / password
        // TODO: send email validation mail
        // TODO: ...
        return userDao.create(username, password);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userDao.getUserByUsername(username);
    }
}
