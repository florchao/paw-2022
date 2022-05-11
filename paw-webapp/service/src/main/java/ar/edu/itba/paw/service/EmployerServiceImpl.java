package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Employer;
import ar.edu.itba.paw.persistence.EmployerDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmployerServiceImpl implements EmployerService{

    @Autowired
    private EmployerDao employerDao;

    @Override
    public Employer create(String name, long id, byte[] image) {
        name = name.trim().replaceAll(" +", " ");
        return employerDao.create(name, id, image);
    }

    @Override
    public Optional<Employer> getEmployerById(long id) {
        return employerDao.getEmployerById(id);
    }

}
