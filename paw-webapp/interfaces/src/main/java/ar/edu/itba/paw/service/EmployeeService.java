package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Employee;
import ar.edu.itba.paw.model.exception.UserNotFoundException;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {
    Employee getEmployeeById(long id) throws UserNotFoundException;
    Employee create(String name, String location, Long id, String availability, long experienceYears, long hourlyFee, String abilities, byte[] image);
    void editProfile(String name, String location, Long id, String[] availability, long experienceYears, long hourlyFee, String[] abilities, byte [] image);
    void isEmployee(long id) throws UserNotFoundException;
    int getPageNumber(
            String name,
            Long experienceYears,
            String location,
            String availability,
            String abilities,
            long pageSize,
            String orderCriteria);

    List<Employee> getFilteredEmployees(
            String name,
            Long experienceYears,
            String location,
            String availability,
            String abilities,
            Long page,
            long pageSize,
            String orderCriteria);

    long getRatingVoteCount(long idRating);

    float getRating(long employeeId);

}
