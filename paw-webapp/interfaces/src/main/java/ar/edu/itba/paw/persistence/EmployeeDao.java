package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeDao {
    Optional<Employee> getEmployeeById(long id);

    Employee create(String name, String location, String availability);

    Optional<List<Employee>> getEmployees();
}
