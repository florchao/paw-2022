package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.*;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;
@Repository
public class EmployeeJpaDao implements EmployeeDao{
    @PersistenceContext
    private EntityManager em;

    @Override
    public Optional<Employee> getEmployeeById(long id) {
        return Optional.ofNullable(em.find(Employee.class, id));
    }

    @Override
    public Employee create(long id, String name, String location, String availability, long experienceYears, String abilites, byte[] image) {
        Optional<User> user=  Optional.ofNullable(em.find(User.class, id));
        if(user.isPresent()) {
            final Employee employee = new Employee(name.toLowerCase(), location, user.get(), availability, experienceYears, abilites);
            employee.setRating(0);
            employee.setVoteCount(0);
            employee.getId().setImage(image);
            em.persist(employee);
            return employee;
        }
        return null;
    }

    @Override
    public void update(Employee employee, String name, String location, String availability, long experienceYears, String abilites, byte [] image) {
        employee.setName(name);
        employee.setAbilities(abilites);
        employee.setLocation(location);
        employee.setExperienceYears(experienceYears);
        employee.setAvailability(availability);
        employee.getId().setImage(image);
    }

    @Override
    public List<Employee> getEmployees(long pageSize, String orderCriteria) {
        final TypedQuery<Employee> employeeList = em.createQuery("select e from Employee e order by e.rating desc", Employee.class)
                .setFirstResult(0)
                .setMaxResults((int) pageSize);
        return employeeList.getResultList();
    }

    @Override
    public Boolean isEmployee(Employee id) {
        return id.getId().getRole() == 1;
    }

    @Override
    public List<Employee> getFilteredEmployees(String name, Long experienceYears, String location, List<String> availability, List<String> abilities, Long page, long pageSize, String orderCriteria) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SELECT e FROM Employee e where ");
        if (name != null) {
            stringBuilder.append("e.name like '%").append(name.toLowerCase()).append("%'");
            stringBuilder.append(" and   ");
        }
        if (experienceYears != null && experienceYears.intValue() > 0) {
            stringBuilder.append("e.experienceYears >= ").append(experienceYears);
            stringBuilder.append(" and   ");
        }
        if (location != null) {
            stringBuilder.append("e.location like '%").append(location.toLowerCase()).append("%' ");
            stringBuilder.append(" and   ");
        }
        for (String av : availability) {
            stringBuilder.append("e.availability like '%").append(av).append("%'");
            stringBuilder.append(" and   ");
        }
        for (String ability : abilities) {
            stringBuilder.append("e.abilities like '%").append(ability).append("%'");
            stringBuilder.append(" and   ");
        }
        stringBuilder.setLength(stringBuilder.length() - 7);
        if (orderCriteria == null || orderCriteria.equals("")) {
            stringBuilder.append(" order by e.rating desc");
        } else {
            stringBuilder.append(" order by ")
                    .append(orderCriteria)
                    .append(" desc");
        }
        System.out.println("en persistencia mi ordercriteria vale "+orderCriteria);
        System.out.println(stringBuilder);
        TypedQuery<Employee> filteredQuery = em.createQuery(stringBuilder.toString(), Employee.class).setFirstResult((int) (page * pageSize)).setMaxResults((int) pageSize);
        return filteredQuery.getResultList();
    }

    @Override
    public int getPageNumber(String name, Long experienceYears, String location, List<String> availability, List<String> abilities, Long pageSize) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SELECT count(e) FROM Employee e where ");
        if (name != null) {
            stringBuilder.append("e.name like '%").append(name.toLowerCase()).append("%'");
            stringBuilder.append(" and   ");
        }
        if (experienceYears != null && experienceYears.intValue() > 0) {
            stringBuilder.append("e.experienceYears >= ").append(experienceYears);
            stringBuilder.append(" and   ");
        }
        if (location != null) {
            stringBuilder.append("e.location like '%").append(location.toLowerCase()).append("%' ");
            stringBuilder.append(" and   ");
        }
        for (String av : availability) {
            stringBuilder.append("e.availability like '%").append(av).append("%'");
            stringBuilder.append(" and   ");
        }
        for (String ability : abilities) {
            stringBuilder.append("e.abilities like '%").append(ability).append("%'");
            stringBuilder.append(" and   ");
        }
        stringBuilder.setLength(stringBuilder.length() - 7);

        TypedQuery<Long> filteredQuery = em.createQuery(stringBuilder.toString(), Long.class);

        return (int) Math.ceil( (double) filteredQuery.getSingleResult() / pageSize);

    }

    @Override
    public void updateRating(Employee employeeId, float rating) {
        Query contactQuery = em.createQuery("UPDATE Employee e SET e.rating=:newRating where e.id=:user");
        contactQuery.setParameter("user", employeeId.getId());
        contactQuery.setParameter("newRating", rating);
        contactQuery.executeUpdate();
    }

    @Override
    public float getPrevRating(Employee employeeId) {
        TypedQuery<Employee> query = em.createQuery("SELECT e FROM Employee e WHERE e.id=:user", Employee.class);
        query.setParameter("user", employeeId.getId());
        return query.getSingleResult().getRating();
    }

    @Override
    public long getRatingVoteCount(Employee employeeId) {
        TypedQuery<Employee> query = em.createQuery("SELECT e FROM Employee e WHERE e.id=:user", Employee.class);
        query.setParameter("user", employeeId.getId());
        return query.getSingleResult().getVoteCount();
    }

    @Override
    public void incrementVoteCountValue(Employee employeeId) {
        Query contactQuery = em.createQuery("UPDATE Employee e SET e.voteCount =(e.voteCount + 1) where e.id =:user");
        contactQuery.setParameter("user", employeeId.getId());
        contactQuery.executeUpdate();
    }
}
