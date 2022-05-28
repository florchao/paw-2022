package ar.edu.itba.paw.model;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity(name = "Employer")
@Table(name = "employer")
@SecondaryTable(name = "users",
        pkJoinColumns = @PrimaryKeyJoinColumn(name = "userId"))
@Embeddable
public class Employer implements Serializable {
    @Column(length = 100, nullable = false)
    private String name;
    @OneToOne
    @EmbeddedId
    @JoinColumn(name = "employerID", nullable = false)
    private User id;

    public Employer() {
    }

    public Employer(String name, User id) {
        this.name = name;
        this.id = id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(User id) {
        this.id = id;
    }

    public User getId() {
        return id;
    }

}
