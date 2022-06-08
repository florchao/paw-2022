package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.Employee;
import ar.edu.itba.paw.model.Employer;

public interface RaitingDao {
    void udpateRatingsTable(Employee employeeId, Employer employerId, Long rating);

    boolean hasAlreadyRated(Employee employeeId, Employer employerId);
}
