package ar.edu.itba.paw.model;

import javax.persistence.*;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_userid_seq")
    @SequenceGenerator(name = "users_userid_seq", sequenceName = "users_userid_seq", allocationSize = 1)
    @Column(name = "userid")
    private long id;

    @Column(length = 100, nullable = false, unique = true)
    private String username;

    @Column(length = 100, nullable = false)
    private String password;

    @Column(length = 100, nullable = false)
    private int role;

    /* Default */ User() {
        // Just for Hibernate
    }

    public User(long id, String username, String password, int role) {
        super();
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
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
