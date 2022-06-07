package ar.edu.itba.paw.webapp.constraint.validator;

import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.persistence.UserDao;
import ar.edu.itba.paw.webapp.constraint.annotation.UniqueEmailAnnotation;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

public class UniqueEmailValidator implements ConstraintValidator<UniqueEmailAnnotation, String>
{
    @Autowired
    UserDao userDao;

    @Override
    public void initialize(UniqueEmailAnnotation uniqueEmailAnnotation) {
    }

    @Override
    public boolean isValid(String userName, final ConstraintValidatorContext context)
    {
        boolean isValidUser = false;
        if(userName!=null && !userName.isEmpty()) {
            Optional<User> user= userDao.getUserByUsername(userName);
            isValidUser = !user.isPresent();
        } else {
            isValidUser = true;
        }
        return isValidUser;
    }
}