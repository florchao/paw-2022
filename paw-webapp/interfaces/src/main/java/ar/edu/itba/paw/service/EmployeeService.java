package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {
    Optional<Employee> getEmployeeById(long id);
    Employee create(String name, String location, long id, String availability);

    Optional<List<Employee>> getEmployees();
}
