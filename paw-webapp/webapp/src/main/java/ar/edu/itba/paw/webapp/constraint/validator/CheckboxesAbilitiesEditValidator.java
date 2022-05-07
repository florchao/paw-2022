package ar.edu.itba.paw.webapp.constraint.validator;

import ar.edu.itba.paw.webapp.constraint.annotation.CheckboxesAbilitiesEdit;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CheckboxesAbilitiesEditValidator implements ConstraintValidator<CheckboxesAbilitiesEdit,String[]> {

    @Override
    public void initialize(CheckboxesAbilitiesEdit checkboxesAbilitiesEdit) {
    }

    public boolean isValid(String []s, ConstraintValidatorContext cvc) {
        if(s.length == 0) {
            return false;
        }
        String abilities = "Cocinar;Planchar;Cuidado de mascotas;Cuidado de mayores;Cuidado de menores;Cuidados especiales";
        for (String l : s) {
            if(!abilities.contains(l))
                return false;
        }
        return true;
    }
}