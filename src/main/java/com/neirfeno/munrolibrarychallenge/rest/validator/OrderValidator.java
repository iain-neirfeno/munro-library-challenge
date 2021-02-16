package com.neirfeno.munrolibrarychallenge.rest.validator;

import com.neirfeno.munrolibrarychallenge.rest.dto.OrderRequest;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class OrderValidator implements ConstraintValidator<IsOrder, OrderRequest> {

    private String[] properties;

    @Override
    public void initialize(IsOrder constraintAnnotation) {
        properties = constraintAnnotation.properties();
    }

    @Override
    public boolean isValid(OrderRequest orderRequest, ConstraintValidatorContext constraintValidatorContext) {
        return orderRequest.getPropertiesWithDirection().allMatch(p ->
                    Arrays.stream(properties).anyMatch(name -> name.equalsIgnoreCase(p.getProperty()))
                    && (!StringUtils.hasText(p.getDirection())
                        || "desc".equalsIgnoreCase(p.getDirection())
                        || "asc".equalsIgnoreCase(p.getDirection())
                    ));
    }
}
