package ar.edu.itba.paw.webapp.constraint.validator;

import ar.edu.itba.paw.webapp.constraint.annotation.CheckboxesAbilitiesAnnotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;

public class CheckboxesAbilitiesValidator implements ConstraintValidator<CheckboxesAbilitiesAnnotation,String> {

    @Override
    public void initialize(CheckboxesAbilitiesAnnotation checkboxesAbilitiesAnnotation) {
    }

    public boolean isValid(String s, ConstraintValidatorContext cvc) {
        if(s == null)
            return true;
        String abilities = "Cocinar;Planchar;Cuidado de mascotas;Cuidado de mayores;Cuidado de menores;Cuidados especiales";
        List<String> list= Arrays.asList(s.split(";"));
        for (String l : list) {
            if(!abilities.contains(l))
                return false;
        }
        return true;
    }
}