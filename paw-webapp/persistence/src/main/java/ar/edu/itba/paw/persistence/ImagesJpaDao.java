package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.Images;
import ar.edu.itba.paw.model.User;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
       Optional<Images> images = Optional.ofNullable(em.find(Images.class, userId));
       if(images.isPresent())
        return Optional.ofNullable(images.get().getImage());
       return Optional.empty();
    }

    @Override
    @Transactional
    public void updateProfileImage(long user, byte[] image) {
        Optional<Images> images = Optional.ofNullable(em.find(Images.class, user));
        if(images.isPresent()){
            em.persist(images.get());
            images.get().setImage(image);
        }
    }

    @Override
    @Transactional
    public Images insertImage(User userId, byte[] image) {
        Optional<Images> optionalImages = Optional.ofNullable(em.find(Images.class, userId.getId()));
        if(optionalImages.isPresent()){
            updateProfileImage(userId.getId(), image);
            return optionalImages.get();
        }
        final Images images = new Images(userId, image);
        em.persist(images);
        return images;
    }
}
