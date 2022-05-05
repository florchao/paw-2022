package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Employee;
import ar.edu.itba.paw.model.Experience;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {
    Optional<Employee> getEmployeeById(long id);

    Employee create(String name, String location, Long id, String availability, long experienceYears, String abilities);

    Optional<List<Employee>> getEmployees();

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
}
