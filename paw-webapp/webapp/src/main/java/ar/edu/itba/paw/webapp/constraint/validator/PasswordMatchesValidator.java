package ar.edu.itba.paw.webapp.constraint.validator;

import ar.edu.itba.paw.webapp.constraint.annotation.PasswordMatchesAnnotation;
import ar.edu.itba.paw.webapp.form.EmployerForm;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatchesAnnotation, Object> {
    @Override
    public void initialize(PasswordMatchesAnnotation passwordMatchesAnnotation) {

    }

    @Override
    public boolean isValid(Object candidate, ConstraintValidatorContext arg1) {
        EmployerForm user = (EmployerForm) candidate;
        return user.getPassword().equals(user.getConfirmPassword());
    }
}
