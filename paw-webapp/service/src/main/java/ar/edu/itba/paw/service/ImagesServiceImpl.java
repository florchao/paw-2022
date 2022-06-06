package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Images;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.persistence.ImagesDao;
import ar.edu.itba.paw.persistence.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ImagesServiceImpl implements ImagesService{

    @Autowired
    private ImagesDao imagesDao;
    @Autowired
    private UserDao userDao;

    @Override
    public Optional<byte[]> getProfileImage(Long userId) {
        Optional<User> user = userDao.getUserById(userId);
        if(user.isPresent())
            return imagesDao.getProfileImage(user.get().getId());
        return Optional.empty();
    }

    @Override
    public boolean updateProfileImage(Long userId, byte[] image) {
        Optional<User> user = userDao.getUserById(userId);
        if(user.isPresent()){
                imagesDao.updateProfileImage(user.get().getId(), image);
                return true;
        }
        return false;
    }

    @Override
    public Optional<Images> insertImage(Long userId, byte[] image) {
        Optional<User> user = userDao.getUserById(userId);
        return user.map(value -> imagesDao.insertImage(value, image));
    }
}
