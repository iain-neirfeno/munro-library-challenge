package com.neirfeno.munrolibrarychallenge.rest.validator;

import com.neirfeno.munrolibrarychallenge.rest.dto.MunroQuery;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HeightValidatorTests {

    private static HeightValidator testSubject;

    @BeforeAll
    public static void Setup(){
        testSubject = new HeightValidator();
    }

    @Test
    public void testValidationPassesWhenNeitherProvided(){
        MunroQuery munroQuery = new MunroQuery();
        assertTrue(testSubject.isValid(munroQuery, null));
    }

    @Test
    public void testValidationPassesWhenOnlyMinProvided(){
        MunroQuery munroQuery = new MunroQuery();
        munroQuery.setMinHeight(1);
        assertTrue(testSubject.isValid(munroQuery, null));
    }

    @Test
    public void testValidationPassesWhenOnlyMaxProvided(){
        MunroQuery munroQuery = new MunroQuery();
        munroQuery.setMaxHeight(1);
        assertTrue(testSubject.isValid(munroQuery, null));
    }

    @Test
    public void testValidationPassesWhenMinEqualsMax(){
        MunroQuery munroQuery = new MunroQuery();
        munroQuery.setMinHeight(1);
        munroQuery.setMaxHeight(1);
        assertTrue(testSubject.isValid(munroQuery, null));
    }

    @Test
    public void testValidationPassesWhenMinLessThanMax(){
        MunroQuery munroQuery = new MunroQuery();
        munroQuery.setMinHeight(1);
        munroQuery.setMaxHeight(2);
        assertTrue(testSubject.isValid(munroQuery, null));
    }

    @Test
    public void testValidationFailsWhenMinGreaterThanMax(){
        MunroQuery munroQuery = new MunroQuery();
        munroQuery.setMinHeight(2);
        munroQuery.setMaxHeight(1);
        assertFalse(testSubject.isValid(munroQuery, null));
    }
}
