package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Employee;
import ar.edu.itba.paw.model.Employer;
import ar.edu.itba.paw.persistence.EmployeeDao;
import ar.edu.itba.paw.persistence.EmployerDao;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

@RunWith(MockitoJUnitRunner.Silent.class)
public class EmployerServiceImplTest {
    private static final String NAME = "username";
    private static final long ID = 1;
    private static final byte[] IMAGE = null;

    @Mock
    private EmployerDao mockDao;

    @InjectMocks
    private EmployerService employerService = new EmployerServiceImpl();

    /*
    @Test
    public void testCreate(){

        Mockito.when(mockDao.create(Mockito.eq(NAME), Mockito.eq(ID), Mockito.eq(IMAGE)))
                .thenReturn(new Employer(NAME,ID));

        Optional<Employer> maybeEmployer = Optional.ofNullable(employerService.create(NAME, ID, IMAGE));

        Assert.assertNotNull(maybeEmployer);
        Assert.assertTrue(maybeEmployer.isPresent());
        Assert.assertEquals(NAME, maybeEmployer.get().getName());
    }

    @Test
    public void testCreateAlreadyExists(){
        Mockito.when(mockDao.getEmployerById(Mockito.eq(ID)))
                .thenReturn(Optional.of(new Employer(NAME, ID)));

        Optional<Employer> maybeEmployer = Optional.ofNullable(employerService.create(NAME, ID, IMAGE));

        Assert.assertNotNull(maybeEmployer);
        Assert.assertFalse(maybeEmployer.isPresent());
    }

     */
}