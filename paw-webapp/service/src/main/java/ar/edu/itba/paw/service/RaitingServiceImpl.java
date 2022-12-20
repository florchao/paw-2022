package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Employee;
import ar.edu.itba.paw.model.Employer;
import ar.edu.itba.paw.persistence.EmployeeDao;
import ar.edu.itba.paw.persistence.EmployerDao;
import ar.edu.itba.paw.persistence.RaitingDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class RaitingServiceImpl implements RaitingService {

    @Autowired
    private EmployerDao employerDao;
    @Autowired
    private RaitingDao raitingDao;
    @Autowired
    private EmployeeDao employeeDao;

    @Transactional
    @Override
    public float updateRating(long employeeId, Long rating, Long employerId) {
        Optional<Employee> employee = employeeDao.getEmployeeById(employeeId);
        Optional<Employer> employer = employerDao.getEmployerById(employerId);
        if (employee.isPresent() && employer.isPresent()) {
            float prevRating = employeeDao.getPrevRating(employee.get());
            float voteCount = employeeDao.getRatingVoteCount(employee.get());
            float newRating = (prevRating * voteCount + rating) / (voteCount + 1L);
            employeeDao.updateRating(employee.get(), newRating);
            raitingDao.udpateRatingsTable(employee.get(), employer.get(), rating);
            employeeDao.incrementVoteCountValue(employee.get());
            return newRating;
        }
        return 0;
    }

    @Override
    public float getRating(long employeeId) {
        Optional<Employee> employee = employeeDao.getEmployeeById(employeeId);
        return employee.map(value -> employeeDao.getPrevRating(value)).orElse(0f);
    }

    @Override
    public boolean hasAlreadyRated(long employeeId, long employerId) {
        Optional<Employee> employee = employeeDao.getEmployeeById(employeeId);
        Optional<Employer> employer = employerDao.getEmployerById(employerId);
        if (employee.isPresent() && employer.isPresent())
            return raitingDao.hasAlreadyRated(employee.get(), employer.get());
        return false;
    }


}
