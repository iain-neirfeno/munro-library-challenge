package com.neirfeno.munrolibrarychallenge.repository;

import com.neirfeno.munrolibrarychallenge.model.HillCategory;
import com.neirfeno.munrolibrarychallenge.model.Munro;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ListMunroRepositoryTests {

    private static ListMunroRepository testSubject;

    @BeforeAll
    public static void Setup(){
        testSubject = new ListMunroRepository(List.of(
                new Munro("Angus", 3000, HillCategory.MUNRO, "NN0001"),
                new Munro("Bert", 1000, HillCategory.TOP, "NN0002"),
                new Munro("Clive", 2000, HillCategory.TOP, "NN0003"),
                new Munro("Dave", 1000, HillCategory.MUNRO, "NN0004")
        ));
    }

    @Test
    public void findByAll(){
        Page<Munro> result = testSubject.findBy(Pageable.unpaged(), null, null, null);
        assertEquals(4, result.getTotalElements());
        assertEquals(1, result.getTotalPages());
        assertEquals(4, result.getSize());
        assertEquals(List.of("Angus", "Bert", "Clive", "Dave"), result.map(Munro::getName).getContent());
    }

    @Test
    public void findByCategory(){
        Page<Munro> result = testSubject.findBy(Pageable.unpaged(), HillCategory.MUNRO, null, null);
        assertEquals(2, result.getTotalElements());
        assertEquals(1, result.getTotalPages());
        assertEquals(2, result.getSize());
        assertEquals(List.of("Angus", "Dave"), result.map(Munro::getName).getContent());
    }

    @Test
    public void findByMinHeight(){
        Page<Munro> result = testSubject.findBy(Pageable.unpaged(), null, 1500, null);
        assertEquals(2, result.getTotalElements());
        assertEquals(1, result.getTotalPages());
        assertEquals(2, result.getSize());
        assertEquals(List.of("Angus", "Clive"), result.map(Munro::getName).getContent());
    }

    @Test
    public void findByMaxHeight(){
        Page<Munro> result = testSubject.findBy(Pageable.unpaged(), null, null, 1500);
        assertEquals(2, result.getTotalElements());
        assertEquals(1, result.getTotalPages());
        assertEquals(2, result.getSize());
        assertEquals(List.of("Bert", "Dave"), result.map(Munro::getName).getContent());
    }

    @Test
    public void findByHeightRange(){
        Page<Munro> result = testSubject.findBy(Pageable.unpaged(), null, 1000, 2000);
        assertEquals(3, result.getTotalElements());
        assertEquals(1, result.getTotalPages());
        assertEquals(3, result.getSize());
        assertEquals(List.of("Bert", "Clive", "Dave"), result.map(Munro::getName).getContent());
    }

    @Test
    public void findByHeightRangeAndCategory(){
        Page<Munro> result = testSubject.findBy(Pageable.unpaged(), HillCategory.TOP, 1000, 2000);
        assertEquals(2, result.getTotalElements());
        assertEquals(1, result.getTotalPages());
        assertEquals(2, result.getSize());
        assertEquals(List.of("Bert", "Clive"), result.map(Munro::getName).getContent());
    }

    @Test
    public void findByAllFirstPage(){
        Page<Munro> result = testSubject.findBy(PageRequest.of(0, 2), null, null, null);
        assertEquals(4, result.getTotalElements());
        assertEquals(2, result.getTotalPages());
        assertEquals(2, result.getSize());
        assertEquals(List.of("Angus", "Bert"), result.map(Munro::getName).getContent());
    }

    @Test
    public void findByAllSecondPage(){
        Page<Munro> result = testSubject.findBy(PageRequest.of(1, 2), null, null, null);
        assertEquals(4, result.getTotalElements());
        assertEquals(2, result.getTotalPages());
        assertEquals(2, result.getSize());
        assertEquals(List.of("Clive", "Dave"), result.map(Munro::getName).getContent());
    }
}
