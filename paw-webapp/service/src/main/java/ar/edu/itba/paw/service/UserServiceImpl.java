package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.exception.PassMatchException;
import ar.edu.itba.paw.model.exception.UserFoundException;
import ar.edu.itba.paw.model.exception.UserNotFoundException;
import ar.edu.itba.paw.persistence.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    @Override
    public User getUserById(long id) throws UserNotFoundException {
        return userDao.getUserById(id).orElseThrow(() -> new UserNotFoundException("User " + id + " not found"));
    }

    @Transactional
    @Override
    public User create(String username, String password, String confPassword, int role) throws UserFoundException, PassMatchException {
        if (userDao.getUserByUsername(username).isPresent()) {
            throw new UserFoundException("There is an account with that email address: "
                    + username);
        }
        if (!password.equals(confPassword)) {
            throw new PassMatchException("Passwords don't match");
        }
        return userDao.create(username, passwordEncoder.encode(password), role);
    }

    @Transactional(readOnly = true)
    @Override
    public User findByUsername(String username) throws UsernameNotFoundException {
        return userDao.getUserByUsername(username).orElseThrow(() -> new UsernameNotFoundException("There is no user with username:" + username));
    }

    @Transactional
    @Override
    public boolean update(String username, String password) {
        String passEncoder = passwordEncoder.encode(password);
        return userDao.update(username, passEncoder);
    }

    @Transactional
    @Override
    public void deleteUser(long id) {
        userDao.deleteUser(id);
    }
}
