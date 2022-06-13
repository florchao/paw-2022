package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.Employee;
import ar.edu.itba.paw.model.Experience;

import java.util.List;
import java.util.Optional;

public interface EmployeeDao {
    Optional<Employee> getEmployeeById(long id);

    Employee create(long id, String name, String location, String availability, long experienceYears, String abilites, byte[] image);

    void update(Employee id, String name, String location, String availability, long experienceYears, String abilites, byte [] image);

   List<Employee> getEmployees(long pageSize);

   Boolean isEmployee(Employee id);

   List<Employee> getFilteredEmployees(String name, Long experienceYears, String location, List<Experience> experiences, List<String> availability, List<String> abilities,Long page, long pageSize);

    int getPageNumber(String name, Long experienceYears, String location, List<Experience> experiences, List<String> availability, List<String> abilities, Long pageSize);

    void updateRating(Employee employeeId, float rating);

    float getPrevRating(Employee employeeId);

    long getRatingVoteCount(Employee employeeId);

    void incrementVoteCountValue(Employee employeeId);

}
