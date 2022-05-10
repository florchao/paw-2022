package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Job;
import ar.edu.itba.paw.model.exception.UserNotFoundException;
import ar.edu.itba.paw.persistence.JobDao;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

@RunWith(MockitoJUnitRunner.Silent.class)
public class JobServiceImplTest {
    @Mock
    private JobDao mockDao;

    @InjectMocks
    private JobService jobService = new JobServiceImpl();

    private static final String LOCATION = "ARGENTINA";
    private static final String AVAILABILITY = "Full Time";
    private static final String DESCRIPTION = "DESCRIPTION";
    private static final long JOB_ID = 1;
    private static final long EMPLOYER_ID = 1;
    private static final long EXPERIENCE_YEARS = 3;
    private static final String ABILITIES = "Cocinar,Planchar";
    private static final String TITLE = "Titulo";

    @Test
    public void testCreateAlreadyExists(){

        Mockito.when(mockDao.getJobById(Mockito.eq(JOB_ID)))
                .thenReturn(Optional.of(new Job(TITLE,LOCATION, JOB_ID, AVAILABILITY, EXPERIENCE_YEARS, ABILITIES, DESCRIPTION)));

        Optional<Job> jobEmployee = Optional.ofNullable(jobService.create(TITLE,LOCATION, JOB_ID, AVAILABILITY, EXPERIENCE_YEARS, ABILITIES, DESCRIPTION));

        Assert.assertNotNull(jobEmployee);
        Assert.assertFalse(jobEmployee.isPresent());
    }
}
