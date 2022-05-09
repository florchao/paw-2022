package ar.edu.itba.paw.webapp.constraint.annotation;

import ar.edu.itba.paw.webapp.constraint.validator.CheckboxesAbilitiesEditValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = CheckboxesAbilitiesEditValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD } )
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckboxesAbilitiesEdit{
    //error message
    public String message() default "Invalid abilities";
    //represents group of constraints
    public Class<?>[] groups() default {};
    //represents additional information about annotation
    public Class<? extends Payload>[] payload() default {};
}