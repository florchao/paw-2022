package ar.edu.itba.paw.webapp.constraint.validator;

import ar.edu.itba.paw.webapp.constraint.annotation.CheckboxesAvailabilityJobAnnotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CheckboxesAvailabilityJobValidator implements ConstraintValidator<CheckboxesAvailabilityJobAnnotation,String> {

    @Override
    public void initialize(CheckboxesAvailabilityJobAnnotation checkboxesAbilitiesRegister) {
    }

    public boolean isValid(String s, ConstraintValidatorContext cvc) {
        System.out.println(s);
        if(s == null)
            return false;
        return s.contains("Jornada completa") || s.contains("Con cama") || s.contains("Media jornada");
    }
}