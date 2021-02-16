package com.neirfeno.munrolibrarychallenge.rest.validator;

import com.neirfeno.munrolibrarychallenge.rest.dto.MunroQuery;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class HeightValidator implements ConstraintValidator<MinHeightNotLessThanMaxHeight, MunroQuery> {

    @Override
    public boolean isValid(MunroQuery munroQuery, ConstraintValidatorContext constraintValidatorContext) {

        return munroQuery.getMinHeight() == null
                || munroQuery.getMaxHeight() == null
                || munroQuery.getMaxHeight() >= munroQuery.getMinHeight();
    }
}
