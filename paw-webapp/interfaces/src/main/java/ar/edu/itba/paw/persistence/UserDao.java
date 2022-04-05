package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.User;

import java.util.Optional;

// DAO  = Data Access Object
public interface UserDao {

    Optional<User> getUserById(long id);
    User create(String username, String password);
}
