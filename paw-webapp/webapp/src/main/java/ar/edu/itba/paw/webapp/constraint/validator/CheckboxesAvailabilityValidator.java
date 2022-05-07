package ar.edu.itba.paw.webapp.constraint.validator;

import ar.edu.itba.paw.webapp.constraint.annotation.CheckboxesAvailabilityRegister;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;

public class CheckboxesAvailabilityValidator implements ConstraintValidator<CheckboxesAvailabilityRegister,String> {
    @Override
    public void initialize(CheckboxesAvailabilityRegister checkboxesAvailabilityRegister) {
    }

    public boolean isValid(String s, ConstraintValidatorContext cvc) {
        if(s == null)
            return true;
        String abilities = "Jornada completa;Con cama;Media jornada";
        List<String> list= Arrays.asList(s.split(","));
        for (String l : list) {
            if(!abilities.contains(l))
                return false;
        }
        return true;
    }
}
