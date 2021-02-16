package com.neirfeno.munrolibrarychallenge.rest.dto;

import com.neirfeno.munrolibrarychallenge.model.HillCategory;
import com.neirfeno.munrolibrarychallenge.rest.validator.MinHeightNotLessThanMaxHeight;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MinHeightNotLessThanMaxHeight(message = "The min height cannot be less than the max height")
public class MunroQuery {
    private HillCategory category;
    private Integer minHeight;
    private Integer maxHeight;

}
