package ar.edu.itba.paw.service;

import java.util.Optional;

public interface ImagesService {
    Optional<byte[]> getProfileImage(Long userId);

    boolean updateProfileImage(Long userId, byte[] image);
}
