package ar.edu.itba.paw.webapp.constraint.validator;

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
