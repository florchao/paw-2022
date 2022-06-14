package ar.edu.itba.paw.webapp.constraint.validator;

import ar.edu.itba.paw.webapp.constraint.annotation.PasswordMatchesAnnotation;
import ar.edu.itba.paw.webapp.constraint.annotation.PasswordMatchesAnnotationEmployee;
import ar.edu.itba.paw.webapp.form.EmployeeForm;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidatorEmployee implements ConstraintValidator<PasswordMatchesAnnotationEmployee, Object> {
    @Override
    public void initialize(PasswordMatchesAnnotationEmployee passwordMatchesAnnotation) {

    }

    @Override
    public boolean isValid(Object candidate, ConstraintValidatorContext arg1) {
        EmployeeForm user = (EmployeeForm) candidate;
        return user.getPassword().equals(user.getConfirmPassword());
    }
}
