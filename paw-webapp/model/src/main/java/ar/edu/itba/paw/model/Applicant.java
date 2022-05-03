package ar.edu.itba.paw.model;

public class Applicant {
    private long jobID;
    private long employeeID;

    private String employerUsername;
    private String jobName;
    private String employeeUsername;

    public Applicant(long jobID, long employeeID) {
        this.jobID = jobID;
        this.employeeID = employeeID;
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
}
