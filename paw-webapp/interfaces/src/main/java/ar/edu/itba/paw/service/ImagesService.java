package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Images;

import java.util.Optional;

public interface ImagesService {
    Optional<byte[]> getProfileImage(Long userId);

    boolean updateProfileImage(Long userId, byte[] image);
    Optional<Images> insertImage(Long userId, byte[] image);
}
