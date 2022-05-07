package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.exception.PassMatchException;
import ar.edu.itba.paw.model.exception.UserFoundException;
import ar.edu.itba.paw.model.exception.UserNotFoundException;

import java.util.Optional;

public interface UserService {
    Optional<User> getUserById(long id) throws UserNotFoundException;

    User create(String username, String password, String confirmPassword, int role) throws UserFoundException, PassMatchException, UserNotFoundException;

    Optional<User> findByUsername(String username);

    boolean update(String username, String password);

    Optional<byte[]> getProfileImage(Long userId);

    boolean updateProfileImage(Long userId, byte[] image);
}
