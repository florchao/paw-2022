package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Employee;
import ar.edu.itba.paw.persistence.EmployeeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService{

    @Autowired
    private EmployeeDao employeeDao;

    @Override
    public Optional<Employee> getEmployeeById(long id) {
        return employeeDao.getEmployeeById(id);
    }

    @Override
    public Employee create(String name, String location, long id, String availability) {
        //TODO: validate name, location, id, etc
        return employeeDao.create(name, location, availability);
    }

    @Override
    public Optional<List<Employee>> getEmployees() {
        return employeeDao.getEmployees();
    }
}
