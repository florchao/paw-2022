package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.Employee;
import ar.edu.itba.paw.model.Experience;

import java.util.List;
import java.util.Optional;

public interface EmployeeDao {
    Optional<Employee> getEmployeeById(long id);

    Employee create(long id, String name, String location, String availability, long experienceYears, String abilites, byte[] image);

    void update(long id, String name, String location, String availability, long experienceYears, String abilites);

    Optional<List<Employee>> getEmployees(long pageSize);

    Optional<Boolean> isEmployee(long id);

    Optional<List<Employee>> getFilteredEmployees(String name, Long experienceYears, String location, List<Experience> experiences, List<String> availability, List<String> abilities,Long page, long pageSize);

    int getPageNumber(String name, Long experienceYears, String location, List<Experience> experiences, List<String> availability, List<String> abilities, Long pageSize);
}
