package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.User;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class UserJpaDao implements UserDao{
    @PersistenceContext
    private EntityManager em;

    @Override
    public List<User> getAll(int page) {
        return null;
    }

    @Override
    public Optional<User> getUserById(long id) {
        return Optional.empty();
    }

    @Override
    public User create(String username, String password, int role) {
        return null;
    }

    @Override
    public Optional<User> getUserByUsername(String username) {
        return Optional.empty();
    }

    @Override
    public boolean update(String username, String password) {
        return false;
    }

    @Override
    public Optional<byte[]> getProfileImage(Long userId) {
        return Optional.empty();
    }

    @Override
    public boolean updateProfileImage(Long userId, byte[] image) {
        return false;
    }
}
