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
        for (String l : s) {
            try {
                if (Integer.parseInt(l) < 1 || Integer.parseInt(l) > 3)
                    return false;
            }catch (NumberFormatException e){
                return false;
            }
        }
        return true;
    }
}
