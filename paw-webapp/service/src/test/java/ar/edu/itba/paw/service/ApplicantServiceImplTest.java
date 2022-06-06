package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Applicant;
import ar.edu.itba.paw.model.Employee;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.persistence.ApplicantDao;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;
    /*
@RunWith(MockitoJUnitRunner.Silent.class)
public class ApplicantServiceImplTest {
    private static final long JOB_ID = 1;
    private static final long EMPLOYEE_ID = 3;

    @Mock
    private ApplicantDao mockDao;

    @InjectMocks
    private ApplicantService applicantService = new ApplicantServiceImpl();


    @Test
    public void testCreate(){
        Mockito.when(mockDao.create(Mockito.eq(JOB_ID), Mockito.eq(EMPLOYEE_ID)))
                .thenReturn(new Applicant(JOB_ID, EMPLOYEE_ID));

        Optional<Applicant> applicant = Optional.ofNullable(applicantService.create(JOB_ID, EMPLOYEE_ID));

        Assert.assertNotNull(applicant);
        Assert.assertTrue(applicant.isPresent());
        Assert.assertEquals(JOB_ID, applicant.get().getJobID());
        Assert.assertEquals(EMPLOYEE_ID, applicant.get().getEmployeeID());
    }
}
     */


