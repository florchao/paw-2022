package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.exception.PassMatchException;
import ar.edu.itba.paw.model.exception.UserFoundException;

import java.util.Optional;

public interface UserService {
    Optional<User> getUserById(long id);

    User create(String username, String password, String confirmPassword) throws UserFoundException, PassMatchException;

    Optional<User> findByUsername(String username);

    boolean update(String username, String password);
}
