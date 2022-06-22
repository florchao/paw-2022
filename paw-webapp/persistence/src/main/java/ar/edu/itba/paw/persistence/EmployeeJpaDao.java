package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.*;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    public Employee create(User user, String name, String location, String availability, long experienceYears, String abilites, byte[] image) {
        final Employee employee = new Employee(name.toLowerCase(), location.toLowerCase(), user, availability, experienceYears, abilites);
        employee.setRating(0);
        employee.setVoteCount(0);
        employee.getId().setImage(image);
        em.persist(employee);
        return employee;
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
    public List<Employee> getEmployees(long pageSize) {
        final TypedQuery<Employee> employeeList = em.createQuery("select e from Employee e", Employee.class)
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

        Map<String, Object> paramMap = new HashMap<>();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SELECT e FROM Employee e where ");

        if (name != null) {
            if (location == null) {
                stringBuilder.append("(e.name like :name or e.location like :nameLocation )");
                paramMap.put("name", '%' + name.toLowerCase() + '%');
                paramMap.put("nameLocation", '%' + name.toLowerCase() + '%');
                stringBuilder.append(" and   ");
            } else {
                stringBuilder.append("e.name like :name ");
                paramMap.put("name", '%' + name.toLowerCase() + '%');
            }
        }
        if (experienceYears != null && experienceYears.intValue() > 0) {
            stringBuilder.append("e.experienceYears >= :experienceYears ");
            paramMap.put("experienceYears", experienceYears);
            stringBuilder.append(" and   ");
        }
        if (location != null) {
            stringBuilder.append("e.location like :location ");
            paramMap.put("location", '%' + location.toLowerCase() + '%');
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
        TypedQuery<Employee> filteredQuery = em.createQuery(stringBuilder.toString(), Employee.class).setFirstResult((int) (page * pageSize)).setMaxResults((int) pageSize);
        for (String key : paramMap.keySet()) {
            filteredQuery.setParameter(key, paramMap.get(key));
        }
        return filteredQuery.getResultList();
    }

    @Override
    public int getPageNumber(String name, Long experienceYears, String location, List<String> availability, List<String> abilities, Long pageSize) {
        StringBuilder stringBuilder = new StringBuilder();
        Map<String, Object> paramMap = new HashMap<>();
        stringBuilder.append("SELECT count(e) FROM Employee e where ");

        if (name != null) {
            if (location == null) {
                stringBuilder.append("(e.name like :name or e.location like :nameLocation )");
                paramMap.put("name", '%' + name.toLowerCase() + '%');
                paramMap.put("nameLocation", '%' + name.toLowerCase() + '%');
                stringBuilder.append(" and   ");
            } else {
                stringBuilder.append("e.name like :name ");
                paramMap.put("name", '%' + name.toLowerCase() + '%');
            }
        }
        if (experienceYears != null && experienceYears.intValue() > 0) {
            stringBuilder.append("e.experienceYears >= :experienceYears ");
            paramMap.put("experienceYears", experienceYears);
            stringBuilder.append(" and   ");
        }
        if (location != null) {
            stringBuilder.append("e.location like :location ");
            paramMap.put("location", '%' + location.toLowerCase() + '%');
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
        for (String key : paramMap.keySet()) {
            filteredQuery.setParameter(key, paramMap.get(key));
        }

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
