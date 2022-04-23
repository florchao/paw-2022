package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Employee;
import ar.edu.itba.paw.model.Experience;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {
    Optional<Employee> getEmployeeById(long id);
    Employee create(String name, String location, long id, String availability, long experienceYears, String abilities);

    Optional<List<Employee>> getEmployees();

    Optional<List<Employee>> getFilteredEmployees(
            long experienceYears,
            String location,
            List<Experience> experiences,
            String availability,
            String abilities
    );
}
