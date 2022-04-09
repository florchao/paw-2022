package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Employee;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.persistence.EmployeeDao;
import ar.edu.itba.paw.persistence.UserDao;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeServiceImplTest {
    private static final String LOCATION = "ARGENTINA";
    private static final String AVAILABILITY = "Full Time";
    private static final String NAME = "username";
    private static final long ID = 1;

    @Mock
    private EmployeeDao mockDao;

    @InjectMocks
    private EmployeeService employeeService = new EmployeeServiceImpl();

    @Test
    public void testCreate(){

        Mockito.when(mockDao.create(Mockito.eq(ID), Mockito.eq(NAME), Mockito.eq(LOCATION), Mockito.eq(AVAILABILITY)))
                .thenReturn(new Employee(NAME, LOCATION,ID,  AVAILABILITY));

        Optional<Employee> maybeEmployee = Optional.ofNullable(employeeService.create(NAME, LOCATION, ID, AVAILABILITY));

        Assert.assertNotNull(maybeEmployee);
        Assert.assertTrue(maybeEmployee.isPresent());
        Assert.assertEquals(NAME, maybeEmployee.get().getName());
        Assert.assertEquals(LOCATION, maybeEmployee.get().getLocation());
        Assert.assertEquals(AVAILABILITY, maybeEmployee.get().getAvailability());
    }

    @Test
    public void testCreateAlreadyExists(){

        Mockito.when(mockDao.getEmployeeById(Mockito.eq(ID)))
                .thenReturn(Optional.of(new Employee(NAME,LOCATION, ID, AVAILABILITY)));

        Optional<Employee> maybeEmployee = Optional.ofNullable(employeeService.create(NAME, LOCATION, ID, AVAILABILITY));

        Assert.assertNotNull(maybeEmployee);
        Assert.assertFalse(maybeEmployee.isPresent());
    }

    @Test
    public  void testGetUserById(){

        Mockito.when(mockDao.create(Mockito.eq(ID), Mockito.eq(NAME), Mockito.eq(LOCATION), Mockito.eq(AVAILABILITY)))
                .thenReturn(new Employee(NAME, LOCATION, ID, AVAILABILITY));

        Optional<Employee> maybeUser = employeeService.getEmployeeById(1);

        Assert.assertNotNull(maybeUser);
        Assert.assertFalse(maybeUser.isPresent());
    }

}