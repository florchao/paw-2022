package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.exception.PassMatchException;
import ar.edu.itba.paw.model.exception.UserFoundException;
import ar.edu.itba.paw.model.exception.UserNotFoundException;
import ar.edu.itba.paw.persistence.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserDao userDao;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public Optional<User> getUserById(long id) {
        Optional<User> user = userDao.getUserById(id);
        if(!user.isPresent()){
            throw new UserNotFoundException("User " + id + " not found");
        }
        return user;
    }

    @Override
    public User create(String username, String password, String confPassword, int role) throws UserFoundException, PassMatchException {
        // TODO: validate username / password
        // TODO: send email validation mail
        // TODO: ...
        if (findByUsername(username).isPresent()) {
            throw new UserFoundException("There is an account with that email address: "
                    + username);
        }
        if(!password.equals(confPassword)){
            throw new PassMatchException("Passwords don't match");
        }
        return userDao.create(username, passwordEncoder.encode(password), role);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userDao.getUserByUsername(username);
    }

    @Override
    public boolean update(String username, String password) {
        String passEncoder = passwordEncoder.encode(password);
        return userDao.update(username, passEncoder);
    }

    @Override
    public Optional<byte[]> getProfileImage(Long userId) {
        Optional<byte[]> image = userDao.getProfileImage(userId);
        if(!image.isPresent())
            //retornar la default
            return userDao.getProfileImage(14L);
        return image;
    }

    @Override
    public boolean updateProfileImage(Long userId, byte[] image) {
        return userDao.updateProfileImage(userId, image);
    }
}
