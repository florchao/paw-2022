package ar.edu.itba.paw.model;

public class Applicant {
    private long jobID;
    private long employeeID;
    private String employerUsername;
    private String employeeUsername;
    private String jobName;
    private String employeeName;

    public Applicant(long jobID, long employeeID) {
        this.jobID = jobID;
        this.employeeID = employeeID;
    }

    public Applicant(long jobID, String employerUsername, String jobName) {
        this.jobID = jobID;
        this.employerUsername = employerUsername;
        this.jobName = jobName;
    }

    public Applicant(long jobID, long employeeID, String employeeName, String employeeUsername) {
        this.jobID = jobID;
        this.employeeID = employeeID;
        this.employeeName = employeeName;
        this.employeeUsername = employeeUsername;
    }

    public long getJobID() {
        return jobID;
    }

    public void setJobID(long jobID) {
        this.jobID = jobID;
    }

    public long getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(long employeeID) {
        this.employeeID = employeeID;
    }

    public String getEmployerUsername() {
        return employerUsername;
    }

    public void setEmployerUsername(String employerUsername) {
        this.employerUsername = employerUsername;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmployeeUsername() {
        return employeeUsername;
    }

    public void setEmployeeUsername(String employeeUsername) {
        this.employeeUsername = employeeUsername;
    }
}
