package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Employee;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.exception.UserNotFoundException;
import ar.edu.itba.paw.persistence.EmployeeDao;
import ar.edu.itba.paw.persistence.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeDao employeeDao;

    @Autowired
    private UserDao userDao;

    @Transactional(readOnly = true)
    @Override
    public Employee getEmployeeById(long id) {
        return employeeDao.getEmployeeById(id).orElseThrow(() -> new UserNotFoundException("employee with id" + id + "not found"));
    }

    @Transactional
    @Override
    public void editProfile(String name, String location, Long id, String[] availability, long experienceYears, long hourlyFee, String[] abilities) {
        StringBuilder abilitiesSB = new StringBuilder();
        StringBuilder availabilitySB = new StringBuilder();
        name = name.trim().replaceAll(" +", " ");
        location = location.trim().replaceAll(" +", " ");
        for (String ab : abilities) {
            abilitiesSB.append(ab).append(",");
        }
        for (String av : availability) {
            availabilitySB.append(av).append(",");
        }
        if (abilitiesSB.length() == 0)
            abilitiesSB.setLength(0);
        else
            abilitiesSB.setLength(abilitiesSB.length() - 1);
        if (availabilitySB.length() == 0)
            availabilitySB.setLength(0);
        else
            availabilitySB.setLength(availabilitySB.length() - 1);
        Optional<Employee> employee = employeeDao.getEmployeeById(id);
        if (employee.isPresent())
            employeeDao.update(employee.get(), name, location, availabilitySB.toString(), experienceYears, hourlyFee, abilitiesSB.toString());
    }

    @Transactional
    @Override
    public Employee create(String name, String location, Long id, String availability, long experienceYears, long hourlyFee, String abilities) {
        name = name.trim().replaceAll(" +", " ");
        location = location.trim().replaceAll(" +", " ");
        Optional<User> user = userDao.getUserById(id);
        if (user.isPresent())
            return employeeDao.create(user.get(), name, location, availability, experienceYears, hourlyFee, abilities);
        return null;
    }

    @Transactional(readOnly = true)
    @Override
    public void isEmployee(long id) throws UserNotFoundException {
        Optional<Employee> employee = employeeDao.getEmployeeById(id);
        if (employee.isPresent()) {
            Boolean exists = employeeDao.isEmployee(employee.get());
            if (!exists)
                throw new UserNotFoundException("Employee " + id + " not found");
        }
    }

    @Transactional
    @Override
    public int getPageNumber(String name, Long experienceYears, String location, String availability, String abilities, long pageSize, String orderCriteria) {
        List<String> availabilityList;
        if (availability != null) {
            availabilityList = Arrays.asList(availability.split(","));
        } else {
            availabilityList = Collections.emptyList();
        }
        List<String> abilitiesList;
        if (abilities != null) {
            abilitiesList = Arrays.asList(abilities.split(","));
        } else {
            abilitiesList = Collections.emptyList();
        }
        List<String> locationList;
        if (location != null) {
            locationList = Arrays.asList(location.split(","));
        } else {
            locationList = Collections.emptyList();
        }
        return employeeDao.getPageNumber(name, experienceYears, locationList, availabilityList, abilitiesList, pageSize, orderCriteria);
    }

    @Transactional
    @Override
    public List<Employee> getFilteredEmployees(
            String name,
            Long experienceYears,
            String location,
            String availability,
            String abilities,
            Long page,
            long pageSize,
            String orderCriteria
    ) {
        if (name == null && experienceYears == null && location == null && availability == null && abilities == null && page == 0 && orderCriteria == null) {
            return employeeDao.getEmployees(pageSize);
        }
        List<String> availabilityList = new ArrayList<>();
        if (availability != null) {
            availabilityList = Arrays.asList(availability.split(","));
        }
        List<String> abilitiesList = new ArrayList<>();
        if (abilities != null) {
            abilitiesList = Arrays.asList(abilities.split(","));
        }
        List<String> locationList = new ArrayList<>();
        if (location != null) {
            locationList = Arrays.asList(location.split(","));
        }
        return employeeDao.getFilteredEmployees(name, experienceYears, locationList, availabilityList, abilitiesList, page, pageSize, orderCriteria);
    }

    @Override
    public long getRatingVoteCount(long idRating) {
        Optional<Employee> employee = employeeDao.getEmployeeById(idRating);
        return employee.map(value -> employeeDao.getRatingVoteCount(value)).orElse(0L);
    }

    @Override
    public float getRating(long employeeId) {
        Optional<Employee> employee = employeeDao.getEmployeeById(employeeId);
        return employee.map(value -> employeeDao.getPrevRating(value)).orElse(0F);
    }


}
