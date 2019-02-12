package org.hibernate.validator.bugs.constraints;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Target({TYPE, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = Magic.Validator.class)
public @interface Magic {



    static class Validator implements ConstraintValidator<Magic, Object> {
        @Override
        public void initialize(Magic a) {
        }

        @Override
        public boolean isValid(Object t, ConstraintValidatorContext cvc) {
            return true;
        }
    }
}
