package ar.edu.itba.paw.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


@Entity(name = "Job")
@Table(name = "jobs")
@SecondaryTable(name = "employer",
        pkJoinColumns = @PrimaryKeyJoinColumn(name = "employerID"))
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

    public Job(){}

    public Job(String title, String location, Employer employerId, String availability, long experienceYears, String abilities, String description) {
        this.title = title;
        this.location = location;
        this.employerId = employerId;
        this.availability = availability;
        this.experienceYears = experienceYears;
        this.abilities = abilities;
        this.description = description;
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

    public Job(String title, String location, long jobId, String availability, long experienceYears, String abilities, String description) {
        this.title = title;
        this.location = location;
        this.jobId = jobId;
        this.availability = availability;
        this.experienceYears = experienceYears;
        this.abilities = abilities;
        this.description = description;
    }

    public Job(String title, String location, long jobId, String availability, long experienceYears, String abilities, String description, String employerName) {
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

    public void employerNameToUpper() {
        StringBuilder finalName = new StringBuilder();
        for (String word : employerId.getName().split(" ")) {
            finalName.append(word.substring(0, 1).toUpperCase()).append(word.substring(1)).append(" ");
        }
        finalName.setLength(finalName.length() - 1);
        employerId.setName(finalName.toString());
    }
}
