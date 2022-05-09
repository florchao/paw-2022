package ar.edu.itba.paw.webapp.constraint.validator;

import ar.edu.itba.paw.webapp.constraint.annotation.CheckboxesAvailability;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CheckboxesAvailabilityValidator implements ConstraintValidator<CheckboxesAvailability,String[]> {
    @Override
    public void initialize(CheckboxesAvailability checkboxesAvailability) {
    }

    public boolean isValid(String []s, ConstraintValidatorContext cvc) {
        if(s == null)
            return true;
        String abilities = "Jornada completa;Con cama;Media jornada";
        for (String l : s) {
            if(!abilities.contains(l))
                return false;
        }
        return true;
    }
}
