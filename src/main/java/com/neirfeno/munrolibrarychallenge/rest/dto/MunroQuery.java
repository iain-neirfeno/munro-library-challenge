package com.neirfeno.munrolibrarychallenge.rest.dto;

import com.neirfeno.munrolibrarychallenge.model.HillCategory;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MunroQuery {
    private HillCategory category;
    private Integer minHeight;
    private Integer maxHeight;

}
