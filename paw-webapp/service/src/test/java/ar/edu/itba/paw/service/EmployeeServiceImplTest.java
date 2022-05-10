package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Employee;
import ar.edu.itba.paw.persistence.EmployeeDao;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

@RunWith(MockitoJUnitRunner.Silent.class)
public class EmployeeServiceImplTest {
    private static final String LOCATION = "ARGENTINA";
    private static final String AVAILABILITY = "Full Time";
    private static final String NAME = "username";
    private static final long ID = 1;
    private static final long EXPERIENCE_YEARS = 3;
    private static final String ABILITIES = "Cocinar,Planchar";
    private static final byte[] IMAGE = null;

    @Mock
    private EmployeeDao mockDao;

    @InjectMocks
    private EmployeeService employeeService = new EmployeeServiceImpl();

    @Test
    public void testCreate(){

        Mockito.when(mockDao.create(Mockito.eq(ID), Mockito.eq(NAME), Mockito.eq(LOCATION), Mockito.eq(AVAILABILITY), Mockito.eq(EXPERIENCE_YEARS), Mockito.eq(ABILITIES), Mockito.eq(IMAGE)))
                .thenReturn(new Employee(NAME, LOCATION,ID, AVAILABILITY, EXPERIENCE_YEARS, ABILITIES));

        Optional<Employee> maybeEmployee = Optional.ofNullable(employeeService.create(NAME, LOCATION, ID, AVAILABILITY, EXPERIENCE_YEARS, ABILITIES, IMAGE));

        Assert.assertNotNull(maybeEmployee);
        Assert.assertTrue(maybeEmployee.isPresent());
        Assert.assertEquals(NAME, maybeEmployee.get().getName());
        Assert.assertEquals(LOCATION, maybeEmployee.get().getLocation());
        Assert.assertEquals(AVAILABILITY, maybeEmployee.get().getAvailability());
        Assert.assertEquals(EXPERIENCE_YEARS, maybeEmployee.get().getExperienceYears());
        Assert.assertEquals(ABILITIES, maybeEmployee.get().getAbilities());
    }

    @Test
    public void testCreateAlreadyExists(){

        Mockito.when(mockDao.getEmployeeById(Mockito.eq(ID)))
                .thenReturn(Optional.of(new Employee(NAME,LOCATION, ID, AVAILABILITY, EXPERIENCE_YEARS, ABILITIES)));

        Optional<Employee> maybeEmployee = Optional.ofNullable(employeeService.create(NAME, LOCATION, ID, AVAILABILITY, EXPERIENCE_YEARS, ABILITIES, IMAGE));

        Assert.assertNotNull(maybeEmployee);
        Assert.assertFalse(maybeEmployee.isPresent());
    }

}