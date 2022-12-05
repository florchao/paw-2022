package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Employee;
import ar.edu.itba.paw.model.Employer;
import ar.edu.itba.paw.model.Review;
import ar.edu.itba.paw.persistence.EmployeeDao;
import ar.edu.itba.paw.persistence.EmployerDao;
import ar.edu.itba.paw.persistence.ReviewDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ReviewServiceImpl implements ReviewService{

    @Autowired
    private ReviewDao reviewDao;
    @Autowired
    private EmployeeDao employeeDao;
    @Autowired
    private EmployerDao employerDao;

    @Transactional
    @Override
    public Review create(long employeeId, long employerId, String review, Date created, boolean forEmployee) {
        System.out.println("EMPLOYEE ID" + employeeId);
        System.out.println("EMPLOYEE ID" + employerId);
        Optional<Employee> employee = employeeDao.getEmployeeById(employeeId);
        Optional<Employer> employer = employerDao.getEmployerById(employerId);
        System.out.println("ENTRE EN EL SERVICE IMPL");
        System.out.println("EMPLOYEE " + employee.get().getId());
        System.out.println("EMPLOYER " + employer.get().getId());

        if (employee.isPresent() && employer.isPresent()) {
            return reviewDao.create(employee.get(), employer.get(), review, created, forEmployee);
        }
        return null;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Review> getAllReviews(long employeeId, Long id, Long page, int pageSize) {
        Optional<Employee> employee = employeeDao.getEmployeeById(employeeId);
        if(id!=null) {
            Optional<Employer> employer = employerDao.getEmployerById(id);
            if (employee.isPresent() && employer.isPresent())
                return reviewDao.getAllReviews(employee.get(), employer.get(), page, pageSize);
        }else {
            if(employee.isPresent())
                return reviewDao.getAllReviews(employee.get(), null, page, pageSize);
        }
        return Collections.emptyList();
    }

    @Transactional(readOnly = true)
    @Override
    public int getPageNumber(long employeeId, Long id, int pageSize) {
        Optional<Employee> employee = employeeDao.getEmployeeById(employeeId);
        if(id!=null) {
            Optional<Employer> employer = employerDao.getEmployerById(id);
            if (employee.isPresent() && employer.isPresent())
                return reviewDao.getPageNumber(employee.get(), employer.get(), pageSize);
        }
        return 0;
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Review> getMyReview(long employeeId, long id) {
        Optional<Employee> employee = employeeDao.getEmployeeById(employeeId);
        Optional<Employer> employer = employerDao.getEmployerById(id);
        if (employee.isPresent() && employer.isPresent())
            return reviewDao.getMyReview(employee.get(), employer.get());
        return Optional.empty();
    }

    @Override
    public List<Review> getMyProfileReviews(long employeeId, long page, int pageSize) {
        Optional<Employee> employee = employeeDao.getEmployeeById(employeeId);
        if (employee.isPresent() )
            return reviewDao.getMyProfileReviews(employee.get(), page, pageSize);
        return Collections.emptyList();
    }

    @Override
    public int getMyProfileReviewsPageNumber(long employeeId, int pageSize) {
        Optional<Employee> employee = employeeDao.getEmployeeById(employeeId);
        if (employee.isPresent() )
            return reviewDao.getMyProfileReviewsPageNumber(employee.get(), pageSize);
        return 0;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Review> getAllReviewsEmployer(Long employeeId, long employerId, Long page, int pageSize) {
        Optional<Employer> employer = employerDao.getEmployerById(employerId);
        if(employeeId!=null) {
            Optional<Employee> employee = employeeDao.getEmployeeById(employeeId);
            if (employee.isPresent() && employer.isPresent())
                return reviewDao.getAllReviewsEmployer(employee.get(), employer.get(), page, pageSize);
        }else {
            if(employer.isPresent())
                return reviewDao.getAllReviewsEmployer(null, employer.get(), page, pageSize);
        }
        return Collections.emptyList();
    }

    @Transactional(readOnly = true)
    @Override
    public int getPageNumberEmployer(Long employeeId, long employerId, int pageSize) {
        Optional<Employer> employer = employerDao.getEmployerById(employerId);
        if(employeeId!=null) {
            Optional<Employee> employee = employeeDao.getEmployeeById(employeeId);
            if (employee.isPresent() && employer.isPresent())
                return reviewDao.getPageNumberEmployer(employee.get(), employer.get(), pageSize);
        }
        return 0;
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Review> getMyReviewEmployer(long employeeId, long employerId) {
        Optional<Employee> employee = employeeDao.getEmployeeById(employeeId);
        Optional<Employer> employer = employerDao.getEmployerById(employerId);
        if (employee.isPresent() && employer.isPresent())
            return reviewDao.getMyReviewEmployer(employee.get(), employer.get());
        return Optional.empty();
    }

    @Override
    public List<Review> getMyProfileReviewsEmployer(long employerId, long page, int pageSize) {
        Optional<Employer> employer = employerDao.getEmployerById(employerId);
        if (employer.isPresent() )
            return reviewDao.getMyProfileReviewsEmployer(employer.get(), page, pageSize);
        return Collections.emptyList();
    }

    @Override
    public int getMyProfileReviewsEmployerPageNumber(long employerId, int pageSize) {
        Optional<Employer> employer = employerDao.getEmployerById(employerId);
        if (employer.isPresent() )
            return reviewDao.getMyProfileReviewsEmployerPageNumber(employer.get(), pageSize);
        return 0;
    }
}
