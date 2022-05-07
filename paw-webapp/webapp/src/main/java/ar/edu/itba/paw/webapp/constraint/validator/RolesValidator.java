package ar.edu.itba.paw.webapp.constraint.validator;

import ar.edu.itba.paw.webapp.constraint.annotation.RolesAnnotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class RolesValidator implements ConstraintValidator<RolesAnnotation,String> {

    @Override
    public void initialize(RolesAnnotation rolesAnnotation) {
    }

    public boolean isValid(String s, ConstraintValidatorContext cvc) {
        if(s == null)
            return false;
        return s.contains("Empleador") || s.contains("Empleada");
    }
}