package ar.edu.itba.paw.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

@Entity
@Table(name = "profile_images")
public class Images implements Serializable {

    @OneToOne
    @EmbeddedId
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "userId", nullable = false)
    private User userId;

    @Column(nullable = false)
    private byte[] image;

    public Images(User userId, byte[] image) {
        this.userId = userId;
        this.image = image;
    }

    public Images() {
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Images images = (Images) o;
        return Objects.equals(userId, images.userId) && Arrays.equals(image, images.image);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(userId);
        result = 31 * result + Arrays.hashCode(image);
        return result;
    }
}
