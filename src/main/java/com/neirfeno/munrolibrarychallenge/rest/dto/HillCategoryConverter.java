package com.neirfeno.munrolibrarychallenge.rest.dto;

import com.neirfeno.munrolibrarychallenge.model.HillCategory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class HillCategoryConverter implements Converter<String, HillCategory> {
    @Override
    public HillCategory convert(String s) {
        return switch (s.toUpperCase()) {
            case "MUNRO" -> HillCategory.MUNRO;
            case "TOP" -> HillCategory.TOP;
            case "EITHER" -> null;
            default -> throw new IllegalArgumentException("Category must be one of MUNRO, TOP, or EITHER");
        };
    }
}
