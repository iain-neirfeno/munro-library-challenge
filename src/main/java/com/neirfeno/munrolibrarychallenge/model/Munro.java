package com.neirfeno.munrolibrarychallenge.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class Munro {
    @Getter private String name;
    @Getter private int height;
    @Getter private HillCategory hillCategory;
    @Getter private String gridReference;
}
