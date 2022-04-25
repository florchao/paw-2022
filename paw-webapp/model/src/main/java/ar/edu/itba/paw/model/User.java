package ar.edu.itba.paw.model;

public class User {
    private long id;
    private String username;
    private String password;
    private int role;

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
