package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Employer;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.exception.UserNotFoundException;
import ar.edu.itba.paw.persistence.EmployerDao;
import ar.edu.itba.paw.persistence.ImagesDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EmployerServiceImpl implements EmployerService {

    @Autowired
    private EmployerDao employerDao;
    @Autowired
    private ImagesDao imagesDao;

    @Transactional
    @Override
    public Employer create(String name, User id) {
        name = name.trim().replaceAll(" +", " ");
        return employerDao.create(name, id);
    }

    @Transactional(readOnly = true)
    @Override
    public Employer getEmployerById(long id) throws UserNotFoundException {
        return employerDao.getEmployerById(id).orElseThrow(() -> new UserNotFoundException("employer with id:" + id + "not found"));
    }

}
