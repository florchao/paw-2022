package ar.edu.itba.paw.webapp.auth;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class HogarUser extends User {

    private String name;
    private final long userID;

    public HogarUser(String username, String password, Collection<? extends GrantedAuthority> authorities, String name, long userID) {
        super(username, password, authorities);
        this.name = name;
        this.userID = userID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getUserID() {
        return userID;
    }
}
