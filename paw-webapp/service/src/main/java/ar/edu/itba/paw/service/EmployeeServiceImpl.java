package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Employee;
import ar.edu.itba.paw.model.Experience;
import ar.edu.itba.paw.persistence.EmployeeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService{

    @Autowired
    private EmployeeDao employeeDao;

    @Override
    public Optional<Employee> getEmployeeById(long id) {

        Optional<Employee> employee = (employeeDao.getEmployeeById(id));
        if(employee.isPresent()) {
            List<String> availabilityArr = new ArrayList<>(Arrays.asList(employee.get().getAvailability().split(",")));
            List<String> abilitiesArr = new ArrayList<>(Arrays.asList(employee.get().getAbilities().split(",")));
            Employee aux = new Employee(employee.get().getName(), employee.get().getLocation(), id, availabilityArr, employee.get().getExperienceYears(), abilitiesArr);
            return Optional.of(aux);
        }
        return employee;
    }

    @Override
    public Employee create(String name, String location, Long id, String availability, long experienceYears, String abilities) {
        //TODO: validate name, location, id, etc
        return employeeDao.create(id, name, location, availability, experienceYears, abilities);
    }

    @Override
    public Optional<List<Employee>> getEmployees() {
        return employeeDao.getEmployees();
    }

    @Override
    public Optional<List<Employee>> getFilteredEmployees(
            String name,
            Long experienceYears,
            String location,
            List<Experience> experiences,
            String availability,
            String abilities
    ) {
        System.out.println("getEmployees pero filtrados!");
        System.out.println("---------");
        System.out.println(name);
        System.out.println(experienceYears);
        System.out.println(location);
        System.out.println(experiences);
        System.out.println(availability);
        System.out.println(abilities);
        System.out.println("---------");
        if (name == null && experienceYears == null && location == null && experiences == null && availability == null && abilities == null)
            return employeeDao.getEmployees();
        List<String> availabilityList = new ArrayList<>();
        if (availability != null) {
            availabilityList = Arrays.asList(availability.split(","));
        }
        List<String> abilitiesList= new ArrayList<>();
        if (abilities != null) {
            abilitiesList = Arrays.asList(abilities.split(","));
        }
        return employeeDao.getFilteredEmployees(name,experienceYears,location,experiences, availabilityList,abilitiesList);
    }
}
