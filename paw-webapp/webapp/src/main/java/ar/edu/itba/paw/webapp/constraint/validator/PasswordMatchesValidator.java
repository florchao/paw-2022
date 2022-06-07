package ar.edu.itba.paw.webapp.constraint.validator;

import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.webapp.constraint.annotation.PasswordMatchesAnnotation;
import ar.edu.itba.paw.webapp.form.RegisterForm;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatchesAnnotation, Object> {
    @Override
    public void initialize(PasswordMatchesAnnotation passwordMatchesAnnotation) {

    }

    @Override
    public boolean isValid(Object candidate, ConstraintValidatorContext arg1) {
        RegisterForm user = (RegisterForm) candidate;
        return user.getPassword().equals(user.getConfirmPassword());
    }
}
