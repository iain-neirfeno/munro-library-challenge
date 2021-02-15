package com.neirfeno.munrolibrarychallenge.repository;

import com.neirfeno.munrolibrarychallenge.model.HillCategory;
import com.neirfeno.munrolibrarychallenge.model.Munro;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MunroDataSourceTests {

    @Test
    public void testCanLoadFromCsv() throws IOException {

        MunroDataSource testSubject = new MunroDataSource(
                "MunroDataSourceTests/testCanLoadFromCsv.csv",
                "name",
                "height",
                "category",
                "grid ref");

        List<Munro> munros = testSubject.munros(testSubject.munroData());

        assertEquals("Angus", munros.get(0).getName());
        assertEquals(1000, munros.get(0).getHeight());
        assertEquals(HillCategory.TOP, munros.get(0).getHillCategory());
        assertEquals("NN0001", munros.get(0).getGridReference());
    }

    @Test
    public void testIgnoresWhenNameMissing() throws IOException {

        MunroDataSource testSubject = new MunroDataSource(
                "MunroDataSourceTests/testIgnoresWhenNameMissing.csv",
                "name",
                "height",
                "category",
                "grid ref");

        List<Munro> munros = testSubject.munros(testSubject.munroData());

        assertEquals(1, munros.size());
        assertEquals("Clive", munros.get(0).getName());
        assertEquals(2000, munros.get(0).getHeight());
        assertEquals(HillCategory.MUNRO, munros.get(0).getHillCategory());
        assertEquals("NN0003", munros.get(0).getGridReference());
    }

    @Test
    public void testIgnoresWhenHeightMissing() throws IOException {

        MunroDataSource testSubject = new MunroDataSource(
                "MunroDataSourceTests/testIgnoresWhenNameMissing.csv",
                "name",
                "height",
                "category",
                "grid ref");

        List<Munro> munros = testSubject.munros(testSubject.munroData());

        assertEquals(1, munros.size());
        assertEquals("Clive", munros.get(0).getName());
        assertEquals(2000, munros.get(0).getHeight());
        assertEquals(HillCategory.MUNRO, munros.get(0).getHillCategory());
        assertEquals("NN0003", munros.get(0).getGridReference());
    }

    @Test
    public void testIgnoresWhenCategoryMissing() throws IOException {

        MunroDataSource testSubject = new MunroDataSource(
                "MunroDataSourceTests/testIgnoresWhenCategoryMissing.csv",
                "name",
                "height",
                "category",
                "grid ref");

        List<Munro> munros = testSubject.munros(testSubject.munroData());

        assertEquals(1, munros.size());
        assertEquals("Bert", munros.get(0).getName());
        assertEquals(4000, munros.get(0).getHeight());
        assertEquals(HillCategory.MUNRO, munros.get(0).getHillCategory());
        assertEquals("NN0002", munros.get(0).getGridReference());
    }

    @Test
    public void testIgnoresWhenGridRefMissing() throws IOException {

        MunroDataSource testSubject = new MunroDataSource(
                "MunroDataSourceTests/testIgnoresWhenGridRefMissing.csv",
                "name",
                "height",
                "category",
                "grid ref");

        List<Munro> munros = testSubject.munros(testSubject.munroData());

        assertEquals(1, munros.size());
        assertEquals("Clive", munros.get(0).getName());
        assertEquals(2000, munros.get(0).getHeight());
        assertEquals(HillCategory.MUNRO, munros.get(0).getHillCategory());
        assertEquals("NN0003", munros.get(0).getGridReference());
    }

    @Test
    public void testIgnoresWhenHeightIsNotInt() throws IOException {

        MunroDataSource testSubject = new MunroDataSource(
                "MunroDataSourceTests/testIgnoresWhenHeightIsNotInt.csv",
                "name",
                "height",
                "category",
                "grid ref");

        List<Munro> munros = testSubject.munros(testSubject.munroData());

        assertEquals(1, munros.size());
        assertEquals("Clive", munros.get(0).getName());
        assertEquals(2000, munros.get(0).getHeight());
        assertEquals(HillCategory.MUNRO, munros.get(0).getHillCategory());
        assertEquals("NN0003", munros.get(0).getGridReference());
    }

    @Test
    public void testIgnoresWhenCategoryInvalid() throws IOException {

        MunroDataSource testSubject = new MunroDataSource(
                "MunroDataSourceTests/testIgnoresWhenCategoryInvalid.csv",
                "name",
                "height",
                "category",
                "grid ref");

        List<Munro> munros = testSubject.munros(testSubject.munroData());

        assertEquals(1, munros.size());
        assertEquals("Bert", munros.get(0).getName());
        assertEquals(4000, munros.get(0).getHeight());
        assertEquals(HillCategory.MUNRO, munros.get(0).getHillCategory());
        assertEquals("NN0002", munros.get(0).getGridReference());
    }
}
