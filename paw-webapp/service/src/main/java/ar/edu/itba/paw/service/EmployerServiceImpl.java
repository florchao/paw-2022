package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Employer;
import ar.edu.itba.paw.persistence.EmployeeDao;
import ar.edu.itba.paw.persistence.EmployerDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployerServiceImpl implements EmployerService{

    @Autowired
    private EmployerDao employerDao;

    @Override
    public Employer create(String name, long id, byte[] image) {
        return employerDao.create(name, id, image);
    }
}
