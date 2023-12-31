package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.exception.PassMatchException;
import ar.edu.itba.paw.model.exception.UserFoundException;
import ar.edu.itba.paw.model.exception.UserNotFoundException;

import java.util.Optional;

public interface UserService {
    User getUserById(long id) throws UserNotFoundException;

    User create(String username, String password, String confirmPassword, int role) throws UserFoundException, PassMatchException;

    User findByUsername(String username);

    boolean update(String username, String password);

    void deleteUser(long id);

}
