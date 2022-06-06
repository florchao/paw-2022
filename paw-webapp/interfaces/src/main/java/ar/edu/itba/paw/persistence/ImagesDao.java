package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.Images;
import ar.edu.itba.paw.model.User;

import java.util.Optional;

public interface ImagesDao {

    Optional<byte[]> getProfileImage(long userId);

    boolean updateProfileImage(long userId, byte[] image);

    Images insertImage(User userId, byte[] image);
}
