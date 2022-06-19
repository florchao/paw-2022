package ar.edu.itba.paw.webapp.constraint.validator;

import ar.edu.itba.paw.webapp.constraint.annotation.CheckboxesAvailabilityJobAnnotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CheckboxesAvailabilityJobValidator implements ConstraintValidator<CheckboxesAvailabilityJobAnnotation,String> {

    @Override
    public void initialize(CheckboxesAvailabilityJobAnnotation checkboxesAbilitiesRegister) {
    }

    public boolean isValid(String s, ConstraintValidatorContext cvc) {
        if(s == null)
            return false;
        return !(Integer.parseInt(s) < 1 || Integer.parseInt(s) > 3);
    }
}