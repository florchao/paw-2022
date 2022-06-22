package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.User;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
@Primary
public class UserJpaDao implements UserDao{

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<User> getAll(int page) {
        final TypedQuery<User> usersList = em.createQuery("select u from User u", User.class);
       return usersList.getResultList();
    }

    @Override
    public Optional<User> getUserById(long id) {
        return Optional.ofNullable(em.find(User.class, id));
    }

    @Override
    public User create(String username, String password, int role) {
        final User user = new User(username, password, role);
        em.persist(user);
        return user;
    }

    @Override
    public Optional<User> getUserByUsername(String email) {
        final TypedQuery<User> query = em.createQuery("select u from User u where u.email =:email", User.class);
        query.setParameter("email", email);
        return query.getResultList().stream().findFirst();
    }

    @Override
    public boolean update(String username, String password) {
        Optional<User> dbUser = getUserByUsername(username);
        if(!dbUser.isPresent()) return false;
        dbUser.get().setPassword(password);
        return true;
    }

    @Override
    public void deleteUser(long id) {
        Optional<User> user = getUserById(id);
        if(!user.isPresent()) return;
        em.remove(user.get());
    }
}
