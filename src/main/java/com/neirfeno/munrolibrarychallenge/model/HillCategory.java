package com.neirfeno.munrolibrarychallenge.model;

public enum HillCategory {
    MUNRO("Munro"),
    TOP("Munro Top");

    private String name;

    HillCategory(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }
}
