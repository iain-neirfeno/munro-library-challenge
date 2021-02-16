package com.neirfeno.munrolibrarychallenge.rest.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = OrderValidator.class)
@Target( { ElementType.TYPE } )
@Retention(RetentionPolicy.RUNTIME)
public @interface IsOrder {
    String message() default "{error.order}";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
    String[] properties() default {};
}
