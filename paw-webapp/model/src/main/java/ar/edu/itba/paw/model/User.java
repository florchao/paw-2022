package ar.edu.itba.paw.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "User")
@Table(name = "users")
@SecondaryTable(name = "profile_images",
        pkJoinColumns = @PrimaryKeyJoinColumn(name = "userId"))
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_userid_seq")
    @SequenceGenerator(name = "users_userid_seq", sequenceName = "users_userid_seq", allocationSize = 1)
    @Column(name = "userId", nullable = false)
    private long id;

    @Column(length = 100, nullable = false, unique = true)
    private String email;

    @Column(length = 100, nullable = false)
    private String password;

    @Column(nullable = false)
    private int role;

    @Column(table = "profile_images", nullable = false)
    @Basic(fetch = FetchType.LAZY)
    private byte[] image;

    /* Default */ User() {
        // Just for Hibernate
    }

    public User(long id, String username, String password, int role) {
        super();
        this.id = id;
        this.email = username;
        this.password = password;
        this.role = role;
    }

    public User(String username, String password, int role) {
        this.email = username;
        this.password = password;
        this.role = role;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setEmail(String username) {
        this.email = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }
}
