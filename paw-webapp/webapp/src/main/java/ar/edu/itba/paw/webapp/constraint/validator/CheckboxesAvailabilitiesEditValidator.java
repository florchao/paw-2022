package ar.edu.itba.paw.webapp.constraint.validator;

import ar.edu.itba.paw.webapp.constraint.annotation.CheckboxesAbilitiesEdit;
import ar.edu.itba.paw.webapp.constraint.annotation.CheckboxesAvailabilitiesEdit;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CheckboxesAvailabilitiesEditValidator implements ConstraintValidator<CheckboxesAvailabilitiesEdit,String[]> {


    @Override
    public void initialize(CheckboxesAvailabilitiesEdit checkboxesAvailabilitiesEdit) {
    }

    public boolean isValid(String []s, ConstraintValidatorContext cvc) {
        if(s.length == 0)
            return false;
        String abilities = "Jornada completa;Con cama;Media jornada";
        for (String l : s) {
            if(!abilities.contains(l))
                return false;
        }
        return true;
    }
}
