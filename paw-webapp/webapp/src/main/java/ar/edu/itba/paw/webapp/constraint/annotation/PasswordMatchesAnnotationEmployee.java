package ar.edu.itba.paw.webapp.constraint.annotation;

import ar.edu.itba.paw.webapp.constraint.validator.PasswordMatchesValidatorEmployee;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = PasswordMatchesValidatorEmployee.class)
@Target( { ElementType.TYPE} )
@Retention(RetentionPolicy.RUNTIME)
public @interface PasswordMatchesAnnotationEmployee {
    String message() default "Passwords don't match";
    //represents group of constraints
    Class<?>[] groups() default {};
    //represents additional information about annotation
    Class<? extends Payload>[] payload() default {};
}
