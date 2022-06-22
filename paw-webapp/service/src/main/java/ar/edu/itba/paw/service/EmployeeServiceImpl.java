package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Abilities;
import ar.edu.itba.paw.model.Availability;
import ar.edu.itba.paw.model.Employee;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.exception.UserNotFoundException;
import ar.edu.itba.paw.persistence.EmployeeDao;
import ar.edu.itba.paw.persistence.UserDao;
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
    private UserDao userDao;

    @Transactional(readOnly = true)
    @Override
    public Optional<Employee> getEmployeeById(long id) {
        Optional<Employee> employee = (employeeDao.getEmployeeById(id));
        if(!employee.isPresent()){
            return employee;
        }
        List<String> abilitiesArr = new ArrayList<>(Arrays.asList(employee.get().getAbilities().split(",")));
        List<String> availabilityArr = new ArrayList<>(Arrays.asList(employee.get().getAvailability().split(",")));
        Employee aux = new Employee(employee.get().getName(), employee.get().getLocation(), employee.get().getId(), availabilityArr, employee.get().getExperienceYears(), abilitiesArr);
        return Optional.of(aux);
    }

    @Transactional
    @Override
    public void editProfile(String name, String location, Long id, String[] availability, long experienceYears, String[] abilities, byte [] image) {
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
            if(image.length == 0)
                employeeDao.update(employee.get(), name, location, availabilitySB.toString(), experienceYears, abilitiesSB.toString(), employee.get().getId().getImage());
            else
                employeeDao.update(employee.get(), name, location, availabilitySB.toString(), experienceYears, abilitiesSB.toString(), image);
    }

    @Transactional
    @Override
    public Employee create(String name, String location, Long id, String availability, long experienceYears, String abilities, byte[] image) {
        name = name.trim().replaceAll(" +", " ");
        location = location.trim().replaceAll(" +", " ");
        Optional<User> user=  userDao.getUserById(id);
        if(user.isPresent())
            return employeeDao.create(user.get(), name, location, availability, experienceYears, abilities, image);
        return null;
    }

    @Transactional(readOnly = true)
    @Override
    public void isEmployee(long id) throws UserNotFoundException {
        Optional<Employee> employee = employeeDao.getEmployeeById(id);
        if(employee.isPresent()) {
            Boolean exists = employeeDao.isEmployee(employee.get());
            if (!exists)
                throw new UserNotFoundException("Employee " + id + " not found");
        }
    }

    @Transactional
    @Override
    public int getPageNumber(String name, Long experienceYears, String location, String availability, String abilities, long pageSize) {
        List<String> availabilityList = new ArrayList<>();
        if (availability != null) {
            availabilityList = Arrays.asList(availability.split(","));
        }
        List<String> abilitiesList= new ArrayList<>();
        if (abilities != null) {
            abilitiesList = Arrays.asList(abilities.split(","));
        }
        return employeeDao.getPageNumber(name, experienceYears, location, availabilityList, abilitiesList, pageSize);
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
        List<String> abilitiesList= new ArrayList<>();
        if (abilities != null) {
            abilitiesList = Arrays.asList(abilities.split(","));
        }
        return employeeDao.getFilteredEmployees(name,experienceYears,location, availabilityList,abilitiesList,page,pageSize, orderCriteria);
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

    private ArrayList<String> getAbilitiesEs(String[] abilities){
        ArrayList<String> toReturn = new ArrayList<>();
        for (String ability: abilities) {
            toReturn.add(Abilities.getAbilityById(Integer.parseInt(ability)).getNameEs());
        }
        return toReturn;
    }

    private ArrayList<String> getAvailability(String[] availabilities){
        ArrayList<String> toReturn = new ArrayList<>();
        for (String availability: availabilities) {
            toReturn.add(Availability.getAvailabilityById(Integer.parseInt(availability)).getNameEs());
        }
        return toReturn;
    }


}
