package com.neirfeno.munrolibrarychallenge.rest.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageRequest {
    private Integer page;
    private Integer limit;
}
