package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Employee;
import ar.edu.itba.paw.model.Experience;
import ar.edu.itba.paw.model.exception.UserNotFoundException;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {
    Optional<Employee> getEmployeeById(long id) throws UserNotFoundException;
    Employee create(String name, String location, Long id, String availability, long experienceYears, String abilities, byte[] image);

    void editProfile(String name, String location, Long id, String[] availability, long experienceYears, String[] abilities);
    Optional<List<Employee>> getEmployees();
    void isEmployee(long id);
    int getPageNumber(
            String name,
            Long experienceYears,
            String location,
            List<Experience> experiences,
            String availability,
            String abilities,
            long pageSize);

    Optional<List<Employee>> getFilteredEmployees(
            String name,
            Long experienceYears,
            String location,
            List<Experience> experiences,
            String availability,
            String abilities,
            Long page,
            long pageSize);

    float updateRating(long employeeId, Long rating, Long employerId);

    long getRatingVoteCount(long idRating);

    float getRating(long employeeId);

    boolean hasAlreadyRated(long idRating, long userID);
}
