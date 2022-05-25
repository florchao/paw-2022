package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.Employee;
import ar.edu.itba.paw.model.Experience;
import ar.edu.itba.paw.model.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;
@Repository
public class EmployeeJpaDao implements EmployeeDao{
    @PersistenceContext
    private EntityManager em;

    @Override
    public Optional<Employee> getEmployeeById(long id) {
        return Optional.of(em.find(Employee.class, id));
    }

    @Override
    public Employee create(long id, String name, String location, String availability, long experienceYears, String abilites, byte[] image) {
        Optional<User> user=  Optional.ofNullable(em.find(User.class, id));
        if(user.isPresent()) {
            final Employee employee = new Employee(name, location, user.get(), availability, experienceYears, abilites);
            em.persist(employee);
            return employee;
        }
        return null;
    }

    @Override
    public void update(long id, String name, String location, String availability, long experienceYears, String abilites) {
        Optional<Employee> employee = getEmployeeById(id);
        if (!employee.isPresent())
            return;
        employee.get().setName(name);
        employee.get().setAbilities(abilites);
        employee.get().setLocation(location);
        employee.get().setExperienceYears(experienceYears);
        employee.get().setAvailability(availability);
    }

    @Override
    public Optional<List<Employee>> getEmployees(long pageSize) {
        final TypedQuery<Employee> employeeList = em.createQuery("select e from Employee e", Employee.class);
        return Optional.ofNullable(employeeList.getResultList());
    }

    @Override
    public Optional<Boolean> isEmployee(long id) {
        Optional<Employee> employee = getEmployeeById(id);
        if(employee.isPresent()) {
            if (employee.get().getId().getRole() == 1)
                return Optional.of(true);
        }
        return Optional.of(false);
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
