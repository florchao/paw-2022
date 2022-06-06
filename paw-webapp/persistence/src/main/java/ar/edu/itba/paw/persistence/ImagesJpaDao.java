package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.Images;
import ar.edu.itba.paw.model.User;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Primary
@Repository
public class ImagesJpaDao implements ImagesDao{

    @PersistenceContext
    private EntityManager em;

    @Override
    public Optional<byte[]> getProfileImage(long userId) {
       Images images = em.find(Images.class, userId);
       return Optional.ofNullable(images.getImage());
    }

    @Override
    public boolean updateProfileImage(long userId, byte[] image) {
        Images images = em.find(Images.class, userId);
        if(images != null){
            images.setImage(image);
            return true;
        }
        return false;
    }

    @Override
    public Images insertImage(User userId, byte[] image) {
        final Images images = new Images(userId, image);
        em.persist(images);
        return images;
    }
}
