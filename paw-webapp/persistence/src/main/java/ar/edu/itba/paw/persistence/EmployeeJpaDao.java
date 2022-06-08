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
            final Employee employee = new Employee(name, location, user.get(), availability, experienceYears, abilites);
            employee.getId().setImage(image);
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
    public List<Employee> getEmployees(long pageSize) {
        final TypedQuery<Employee> employeeList = em.createQuery("select e from Employee e", Employee.class)
                .setFirstResult(0)
                .setMaxResults((int) pageSize);
        return employeeList.getResultList();
    }

    @Override
    public Boolean isEmployee(long id) {
        Optional<Employee> employee = getEmployeeById(id);
        return employee.filter(value -> value.getId().getRole() == 1).isPresent();
    }

    @Override
    public List<Employee> getFilteredEmployees(String name, Long experienceYears, String location, List<Experience> experiences, List<String> availability, List<String> abilities, Long page, long pageSize) {
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
        TypedQuery<Employee> filteredQuery = em.createQuery(stringBuilder.toString(), Employee.class).setFirstResult((int) (page * pageSize)).setMaxResults((int) pageSize);
        return filteredQuery.getResultList();
    }

    @Override
    public int getPageNumber(String name, Long experienceYears, String location, List<Experience> experiences, List<String> availability, List<String> abilities, Long pageSize) {
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
    public void updateRating(long employeeId, float rating) {
        System.out.println("mi nuevo rating es: " + rating);
        User user = em.find(User.class, employeeId);
        Query contactQuery = em.createQuery("UPDATE Employee e SET e.rating=:newRating where e.id=:user");
        contactQuery.setParameter("user", user);
        contactQuery.setParameter("newRating", rating);
        contactQuery.executeUpdate();
    }

    @Override
    public float getPrevRating(long employeeId) {
        User user = em.find(User.class, employeeId);
        TypedQuery<Employee> query = em.createQuery("SELECT e FROM Employee e WHERE e.id=:user", Employee.class);
        query.setParameter("user", user);
        return query.getSingleResult().getRating();
    }

    @Override
    public long getRatingVoteCount(long employeeId) {
        User user = em.find(User.class, employeeId);
        TypedQuery<Employee> query = em.createQuery("SELECT e FROM Employee e WHERE e.id=:user", Employee.class);
        query.setParameter("user", user);
        return query.getSingleResult().getVoteCount();
    }

    @Override
    public void incrementVoteCountValue(long employeeId) {
        User user = em.find(User.class, employeeId);
        Query contactQuery = em.createQuery("UPDATE Employee e SET e.voteCount =(e.voteCount + 1) where e.id =:user");
        contactQuery.setParameter("user", user);
        contactQuery.executeUpdate();
    }

    @Override
    public void udpateRatingsTable(long employeeId, Long employerId, Long rating) {
        Employee employee = em.find(Employee.class, employeeId);
        Employer employer = em.find(Employer.class, employerId);
        final Ratings newRating = new Ratings(employee, employer, rating.intValue());
        em.persist(newRating);
    }

    public boolean hasAlreadyRated(long employeeId, long employerId) {
        Employee employee = em.find(Employee.class, employeeId);
        Employer employer = em.find(Employer.class, employerId);
        TypedQuery<Long> filteredQuery = em.createQuery("SELECT count(r) FROM Ratings r WHERE r.employerID=:employer AND r.employeeID=:employee", Long.class);
        filteredQuery.setParameter("employer", employer);
        filteredQuery.setParameter("employee", employee);
        return filteredQuery.getSingleResult() > 0;
    }
}