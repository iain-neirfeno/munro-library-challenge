package com.neirfeno.munrolibrarychallenge.rest.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Constraint(validatedBy = HeightValidator.class)
@Target( { ElementType.TYPE } )
@Retention(RetentionPolicy.RUNTIME)
public @interface MinHeightNotLessThanMaxHeight {
    String message() default "{error.munros.heights}";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
}
