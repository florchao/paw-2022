package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.User;

import java.util.Optional;

public interface ImagesDao {

    Optional<byte[]> getProfileImage(User userId);

    boolean updateProfileImage(User userId, byte[] image);
}
