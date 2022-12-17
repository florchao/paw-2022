package ar.edu.itba.paw.model;


import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;

@Entity(name = "Employer")
@Table(name = "employer")
@Embeddable
public class Employer implements Serializable {
    @Column(length = 100, nullable = false)
    private String name;
    @OneToOne
    @EmbeddedId
    @OnDelete(action = OnDeleteAction.CASCADE)
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

    public void firstWordsToUpper() {
        StringBuilder finalName = new StringBuilder();
        for (String word : getName().split(" ")) {
            finalName.append(word.substring(0, 1).toUpperCase()).append(word.substring(1)).append(" ");
        }
        finalName.setLength(finalName.length() - 1);
        setName(finalName.toString());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employer employer = (Employer) o;
        return Objects.equals(name, employer.name) && Objects.equals(id, employer.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, id);
    }

}
