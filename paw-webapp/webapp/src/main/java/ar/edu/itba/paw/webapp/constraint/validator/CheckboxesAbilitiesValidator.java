package ar.edu.itba.paw.webapp.constraint.validator;

import ar.edu.itba.paw.webapp.constraint.annotation.CheckboxesAbilitiesAnnotation;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CheckboxesAbilitiesValidator implements ConstraintValidator<CheckboxesAbilitiesAnnotation,String[]> {

    @Override
    public void initialize(CheckboxesAbilitiesAnnotation checkboxesAbilitiesAnnotation) {
    }

    public boolean isValid(String []s, ConstraintValidatorContext cvc) {
        if(s == null)
            return true;
        for (String l : s) {
            try {
                if (Integer.parseInt(l) < 1 || Integer.parseInt(l) > 6)
                    return false;
            }catch (NumberFormatException e){
                return false;
            }
        }
        return true;
    }
}