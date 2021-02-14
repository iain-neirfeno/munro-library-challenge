package com.neirfeno.munrolibrarychallenge.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MunroTests {

    @Test
    public void testCanBeConstructedWithData(){
        String name = "test";
        int height = 123;
        HillCategory hillCategory = HillCategory.MUNRO;
        String gridRef = "NN1234";

        Munro testSubject = new Munro(name, height, hillCategory, gridRef);

        assertEquals(name, testSubject.getName());
        assertEquals(height, testSubject.getHeight());
        assertEquals(hillCategory, testSubject.getHillCategory());
        assertEquals(gridRef, testSubject.getGridReference());
    }
}
