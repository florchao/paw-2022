package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Employee;
import ar.edu.itba.paw.model.Employer;
import ar.edu.itba.paw.model.Experience;
import ar.edu.itba.paw.model.exception.UserNotFoundException;
import ar.edu.itba.paw.persistence.EmployeeDao;
import ar.edu.itba.paw.persistence.EmployerDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService{

    @Autowired
    private EmployeeDao employeeDao;

    @Autowired
    private EmployerDao employerDao;

    @Transactional(readOnly = true)
    @Override
    public Optional<Employee> getEmployeeById(long id) {
        Optional<Employee> employee = (employeeDao.getEmployeeById(id));
        if(!employee.isPresent()){
            return employee;
        }
        List<String> availabilityArr = new ArrayList<>(Arrays.asList(employee.get().getAvailability().split(",")));
        List<String> abilitiesArr = new ArrayList<>(Arrays.asList(employee.get().getAbilities().split(",")));
        Employee aux = new Employee(employee.get().getName(), employee.get().getLocation(), employee.get().getId(), availabilityArr, employee.get().getExperienceYears(), abilitiesArr);
        return Optional.of(aux);
    }

    @Transactional
    @Override
    public void editProfile(String name, String location, Long id, String[] availability, long experienceYears, String[] abilities) {
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
        if(abilitiesSB.length() == 0)
            abilitiesSB.setLength(0);
        else
            abilitiesSB.setLength(abilitiesSB.length() - 1);
        if(availabilitySB.length() == 0)
            availabilitySB.setLength(0);
        else
            availabilitySB.setLength(availabilitySB.length() - 1);
        Optional<Employee> employee = employeeDao.getEmployeeById(id);
        if(employee.isPresent())
            employeeDao.update(employee.get(), name, location, availabilitySB.toString(), experienceYears, abilitiesSB.toString());
    }

    @Transactional
    @Override
    public Employee create(String name, String location, Long id, String availability, long experienceYears, String abilities, byte[] image) {
        name = name.trim().replaceAll(" +", " ");
        location = location.trim().replaceAll(" +", " ");
        return employeeDao.create(id, name, location, availability, experienceYears, abilities, image);
    }


    @Transactional(readOnly = true)
    @Override
    public List<Employee> getEmployees() {
        return employeeDao.getEmployees(0);
    }

    @Transactional(readOnly = true)
    @Override
    public void isEmployee(long id) {
        Optional<Employee> employee = employeeDao.getEmployeeById(id);
        if(employee.isPresent()) {
            Boolean exists = employeeDao.isEmployee(employee.get());
            if (!exists)
                throw new UserNotFoundException("Employee " + id + " not found");
        }
    }

    @Transactional
    @Override
    public int getPageNumber(String name, Long experienceYears, String location, List<Experience> experiences, String availability, String abilities, long pageSize) {
        List<String> availabilityList = new ArrayList<>();
        if (availability != null) {
            availabilityList = Arrays.asList(availability.split(","));
        }
        List<String> abilitiesList= new ArrayList<>();
        if (abilities != null) {
            abilitiesList = Arrays.asList(abilities.split(","));
        }
        return employeeDao.getPageNumber(name, experienceYears, location, experiences, availabilityList, abilitiesList, pageSize);
    }

    @Transactional
    @Override
    public List<Employee> getFilteredEmployees(
            String name,
            Long experienceYears,
            String location,
            List<Experience> experiences,
            String availability,
            String abilities,
            Long page,
            long pageSize
    ) {
        if (name == null && experienceYears == null && location == null && experiences == null && availability == null && abilities == null && page == 0) {
            return employeeDao.getEmployees(pageSize);
        }
        List<String> availabilityList = new ArrayList<>();
        if (availability != null) {
            availabilityList = Arrays.asList(availability.split(","));
        }
        List<String> abilitiesList= new ArrayList<>();
        if (abilities != null) {
            abilitiesList = Arrays.asList(abilities.split(","));
        }
        return employeeDao.getFilteredEmployees(name,experienceYears,location,experiences, availabilityList,abilitiesList,page,pageSize);
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
