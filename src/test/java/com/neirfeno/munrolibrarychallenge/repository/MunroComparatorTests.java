package com.neirfeno.munrolibrarychallenge.repository;

import com.neirfeno.munrolibrarychallenge.model.HillCategory;
import com.neirfeno.munrolibrarychallenge.model.Munro;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Sort;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MunroComparatorTests {

    @Test
    public void comparesByName(){
        Sort sort = Sort.by("name");
        MunroComparator testSubject = new MunroComparator(sort);
        Munro left = new Munro("A", 1, HillCategory.MUNRO, "");
        Munro right = new Munro("B", 1, HillCategory.MUNRO, "");

        assertEquals(-1, testSubject.compare(left, right), "Expected left (A) before right (B) with default sort ASC");
    }

    @Test
    public void comparesByNameDesc(){
        Sort sort = Sort.by(Sort.Direction.DESC, "name");
        MunroComparator testSubject = new MunroComparator(sort);
        Munro left = new Munro("A", 1, HillCategory.MUNRO, "");
        Munro right = new Munro("B", 1, HillCategory.MUNRO, "");

        assertEquals(1, testSubject.compare(left, right), "Expected right (B) before left (A) with DESC sort");
    }

    @Test
    public void comparesByNameEqual(){
        Sort sort = Sort.by(Sort.Direction.DESC, "name");
        MunroComparator testSubject = new MunroComparator(sort);
        Munro left = new Munro("A", 1, HillCategory.MUNRO, "");
        Munro right = new Munro("A", 1, HillCategory.MUNRO, "");

        assertEquals(0, testSubject.compare(left, right), "Expected both equal when names are the same");
    }

    @Test
    public void comparesByNameCaseOnly(){
        Sort sort = Sort.by("name");
        MunroComparator testSubject = new MunroComparator(sort);
        Munro left = new Munro("A", 1, HillCategory.MUNRO, "");
        Munro right = new Munro("a", 1, HillCategory.MUNRO, "");

        assertEquals(-1, testSubject.compare(left, right), "Expected left (A) before right (a) when case sensitive");
    }

    @Test
    public void comparesByNameCaseOnlyIgnoreCase(){
        Sort sort = Sort.by(Sort.Order.by("name").ignoreCase());
        MunroComparator testSubject = new MunroComparator(sort);
        Munro left = new Munro("A", 1, HillCategory.MUNRO, "");
        Munro right = new Munro("a", 1, HillCategory.MUNRO, "");

        assertEquals(0, testSubject.compare(left, right), "Expected both equal when names only differ by case and case insensitive");
    }

    @Test
    public void comparesByNameBothNull(){
        Sort sort = Sort.by(Sort.Direction.DESC, "name");
        MunroComparator testSubject = new MunroComparator(sort);
        Munro left = new Munro(null, 1, HillCategory.MUNRO, "");
        Munro right = new Munro(null, 1, HillCategory.MUNRO, "");

        assertEquals(0, testSubject.compare(left, right), "Expected both equal when names are both null");
    }

    @Test
    public void comparesByNameLeftNull(){
        Sort sort = Sort.by("name");
        MunroComparator testSubject = new MunroComparator(sort);
        Munro left = new Munro(null, 1, HillCategory.MUNRO, "");
        Munro right = new Munro("B", 1, HillCategory.MUNRO, "");

        assertEquals(1, testSubject.compare(left, right), "Expected nulls last by default");
    }

    @Test
    public void comparesByNameLeftNullAndNullFirst(){
        Sort sort = Sort.by(Sort.Order.by("name").nullsFirst());
        MunroComparator testSubject = new MunroComparator(sort);
        Munro left = new Munro(null, 1, HillCategory.MUNRO, "");
        Munro right = new Munro("B", 1, HillCategory.MUNRO, "");

        assertEquals(-1, testSubject.compare(left, right), "Expect nulls first when explicit");
    }

    @Test
    public void comparesByNameLeftNullAndDesc(){
        Sort sort = Sort.by(Sort.Direction.DESC, "name");
        MunroComparator testSubject = new MunroComparator(sort);
        Munro left = new Munro(null, 1, HillCategory.MUNRO, "");
        Munro right = new Munro("B", 1, HillCategory.MUNRO, "");

        assertEquals(-1, testSubject.compare(left, right), "Expect nulls first when desc order");
    }
}
