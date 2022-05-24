package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.Employee;
import ar.edu.itba.paw.model.Experience;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;
@Repository
public class EmployeeJpaDao implements EmployeeDao{
    @PersistenceContext
    private EntityManager em;

    @Override
    public Optional<Employee> getEmployeeById(long id) {
        return Optional.empty();
    }

    @Override
    public Employee create(long id, String name, String location, String availability, long experienceYears, String abilites, byte[] image) {
        return null;
    }

    @Override
    public void update(long id, String name, String location, String availability, long experienceYears, String abilites) {

    }

    @Override
    public Optional<List<Employee>> getEmployees(long pageSize) {
        return Optional.empty();
    }

    @Override
    public Optional<Boolean> isEmployee(long id) {
        return Optional.empty();
    }

    @Override
    public Optional<List<Employee>> getFilteredEmployees(String name, Long experienceYears, String location, List<Experience> experiences, List<String> availability, List<String> abilities, Long page, long pageSize) {
        return Optional.empty();
    }

    @Override
    public int getPageNumber(String name, Long experienceYears, String location, List<Experience> experiences, List<String> availability, List<String> abilities, Long pageSize) {
        return 0;
    }
}
