package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Employee;
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
        Employee employee = (employeeDao.getEmployeeById(id)).get();
        List<String> availabilityArr = new ArrayList<>(Arrays.asList(employee.getAvailability().split(",")));
        List<String> abilitiesArr = new ArrayList<>(Arrays.asList(employee.getAbilities().split(",")));
        Employee aux = new Employee(employee.getName(), employee.getLocation(), id, availabilityArr, employee.getExperienceYears(), abilitiesArr);
        System.out.println(aux);
        System.out.println(availabilityArr);
        System.out.println(abilitiesArr);
        System.out.println("-----------");
        return Optional.of(aux);
    }

    @Override
    public Employee create(String name, String location, long id, String availability, long experienceYears, String abilities) {
        //TODO: validate name, location, id, etc
        return employeeDao.create(id, name, location, availability, experienceYears, abilities);
    }

    @Override
    public Optional<List<Employee>> getEmployees() {
        return employeeDao.getEmployees();
    }
}
