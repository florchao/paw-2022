package ar.edu.itba.paw.model;

import javax.persistence.*;

@Entity
@Table(name = "profile_images")
public class Images {

    @OneToOne
    @EmbeddedId
    @JoinColumn(name = "userId", nullable = false)
    User userId;

    @Column(nullable = false)
    byte[] image;

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
}
