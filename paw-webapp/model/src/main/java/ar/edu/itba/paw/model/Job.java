package ar.edu.itba.paw.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Entity(name = "Job")
@Table(name = "jobs")
@Embeddable
public class Job implements Serializable {
    @Column(length = 100, nullable = false)
    private String title;
    @Column(length = 100, nullable = false)
    private String location;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "jobs_jobid_seq")
    @SequenceGenerator(name = "jobs_jobid_seq", sequenceName = "jobs_jobid_seq", allocationSize = 1)
    @Column(name = "jobID", nullable = false)
    private long jobId;

    @ManyToOne
    @Embedded
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "employerID", nullable = false)
    private Employer employerId;
    @Column(length = 100, nullable = false)
    private String availability;
    @Column(nullable = false)
    private long experienceYears;
    @Column(length = 100, nullable = false)
    private String abilities;
    @Column(length = 1000, nullable = false)
    private String description;
    @ElementCollection
    private List<String> availabilityArr;
    @ElementCollection
    private List<String> abilitiesArr;

    @Column
    private boolean opened;

    public Job(){}

    public Job(String title, String location, Employer employerId, String availability, long experienceYears, String abilities, String description) {
        this.title = title;
        this.location = location;
        this.employerId = employerId;
        this.availability = availability;
        this.experienceYears = experienceYears;
        this.abilities = abilities;
        this.description = description;
        this.opened=true;
    }

    public Job(String title, String location, long jobId, Employer employerId, String availability, long experienceYears, String abilities, String description) {
        this.title = title;
        this.location = location;
        this.jobId = jobId;
        this.employerId = employerId;
        this.availability = availability;
        this.experienceYears = experienceYears;
        this.abilities = abilities;
        this.description = description;
    }

    public Job(String title, String location, long jobId, Employer employerId,List<String> availabilityArr, long experienceYears, List<String> abilitiesArr, String description, boolean opened) {
        this.title = title;
        this.location = location;
        this.jobId = jobId;
        this.employerId = employerId;
        this.experienceYears = experienceYears;
        this.description = description;
        this.availabilityArr = availabilityArr;
        this.abilitiesArr = abilitiesArr;
        this.opened = opened;
    }

    public Job(String title, String location, long jobId, String availability, long experienceYears, String abilities, String description) {
        this.title = title;
        this.location = location;
        this.jobId = jobId;
        this.availability = availability;
        this.experienceYears = experienceYears;
        this.abilities = abilities;
        this.description = description;
    }

    public Job(String title, String location, long jobId, List<String>  availability, long experienceYears, List<String> abilities, String description) {
        this.title = title;
        this.location = location;
        this.jobId = jobId;
        this.availabilityArr = availability;
        this.experienceYears = experienceYears;
        this.abilitiesArr = abilities;
        this.description = description;
    }

    public Job(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public long getJobId() {
        return jobId;
    }

    public void setJobId(long jobId) {
        this.jobId = jobId;
    }

    public Employer getEmployerId() {
        return employerId;
    }

    public void setEmployerId(Employer employerId) {
        this.employerId = employerId;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public long getExperienceYears() {
        return experienceYears;
    }

    public void setExperienceYears(long experienceYears) {
        this.experienceYears = experienceYears;
    }

    public String getAbilities() {
        return abilities;
    }

    public void setAbilities(String abilities) {
        this.abilities = abilities;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public List<String> getAvailabilityArr() {
        return availabilityArr;
    }

    public void setAvailabilityArr(List<String> availabilityArr) {
        this.availabilityArr = availabilityArr;
    }

    public List<String> getAbilitiesArr() {
        return abilitiesArr;
    }

    public void setAbilitiesArr(List<String> abilitiesArr) {
        this.abilitiesArr = abilitiesArr;
    }

    public void firstWordsToUpper() {
        StringBuilder finalName = new StringBuilder();
        for (String word : getTitle().split(" ")) {
            finalName.append(word.substring(0, 1).toUpperCase()).append(word.substring(1)).append(" ");
        }
        finalName.setLength(finalName.length() - 1);
        setTitle(finalName.toString());
    }

    public void employerNameToUpper(Employer employerId) {
        StringBuilder finalName = new StringBuilder();
        for (String word : employerId.getName().split(" ")) {
            finalName.append(word.substring(0, 1).toUpperCase()).append(word.substring(1)).append(" ");
        }
        finalName.setLength(finalName.length() - 1);
        employerId.setName(finalName.toString());
        return;
    }

    public void locationNameToUpper(){
        StringBuilder finalName = new StringBuilder();
        for (String word : getLocation().split(" ")) {
            finalName.append(word.substring(0, 1).toUpperCase()).append(word.substring(1)).append(" ");
        }
        finalName.setLength(finalName.length() - 1);
        setLocation(finalName.toString());
    }

    public boolean isOpened() {
        return opened;
    }

    public void setOpened(boolean opened) {
        this.opened = opened;
    }

    public void nameAbilities(String language){
        ArrayList<String> jobAbilities = new ArrayList<>();
        if(language.equals("es"))
            for (String ability: abilitiesArr) {
                jobAbilities.add(Abilities.getAbilityById(Integer.parseInt(ability)).getNameEs());
            }
        else
            for (String ability: abilitiesArr) {
                jobAbilities.add(Abilities.getAbilityById(Integer.parseInt(ability)).getName());
            }
        setAbilitiesArr(jobAbilities);
    }

    public void nameAvailability(String language){
        ArrayList<String> jobAvailability = new ArrayList<>();
        if(language.equals("es"))
            for (String availability: availabilityArr) {
                jobAvailability.add(Availability.getAvailabilityById(Integer.parseInt(availability)).getNameEs());
            }
        else
            for (String availability: availabilityArr) {
                jobAvailability.add(Availability.getAvailabilityById(Integer.parseInt(availability)).getName());
            }
        setAvailabilityArr(jobAvailability);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Job job = (Job) o;
        return jobId == job.jobId && experienceYears == job.experienceYears && opened == job.opened && Objects.equals(title, job.title) && Objects.equals(location, job.location) && Objects.equals(employerId, job.employerId) && Objects.equals(availability, job.availability) && Objects.equals(abilities, job.abilities) && Objects.equals(description, job.description) && Objects.equals(availabilityArr, job.availabilityArr) && Objects.equals(abilitiesArr, job.abilitiesArr);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, location, jobId, employerId, availability, experienceYears, abilities, description, availabilityArr, abilitiesArr, opened);
    }
}
