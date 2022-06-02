package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.Images;
import ar.edu.itba.paw.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

public class ImagesJpaDao implements ImagesDao{

    @PersistenceContext
    private EntityManager em;

    @Override
    public Optional<byte[]> getProfileImage(User userId) {
       Images images = em.find(Images.class, userId);
       return Optional.ofNullable(images.getImage());
    }

    @Override
    public boolean updateProfileImage(User userId, byte[] image) {
        Images images = em.find(Images.class, userId);
        if(images != null){
            images.setImage(image);
            return true;
        }
        return false;
    }
}
