package ar.edu.itba.paw.webapp.constraint.validator;

import ar.edu.itba.paw.webapp.constraint.annotation.CheckboxesAbilitiesAnnotation;
import ar.edu.itba.paw.webapp.constraint.annotation.CheckboxesAbilitiesRegister;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;

public class CheckboxesAbilitiesRegisterValidator implements ConstraintValidator<CheckboxesAbilitiesRegister,String> {

    @Override
    public void initialize(CheckboxesAbilitiesRegister checkboxesAbilitiesRegister) {
    }

    public boolean isValid(String s, ConstraintValidatorContext cvc) {
        if(s == null)
            return false;
        String abilities = "Cocinar;Planchar;Cuidado de mascotas;Cuidado de mayores;Cuidado de menores;Cuidados especiales";
        List<String> list= Arrays.asList(s.split(";"));
        for (String l : list) {
            if(!abilities.contains(l))
                return false;
        }
        return true;
    }
}