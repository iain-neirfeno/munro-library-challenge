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

    @Test
    public void comparesByHeight(){
        Sort sort = Sort.by("height");
        MunroComparator testSubject = new MunroComparator(sort);
        Munro left = new Munro("", 1, HillCategory.MUNRO, "");
        Munro right = new Munro("", 2, HillCategory.MUNRO, "");

        assertEquals(-1, testSubject.compare(left, right), "Expect left (1) before right (2) by default");
    }

    @Test
    public void comparesByHeightDesc(){
        Sort sort = Sort.by(Sort.Direction.DESC, "height");
        MunroComparator testSubject = new MunroComparator(sort);
        Munro left = new Munro("", 1, HillCategory.MUNRO, "");
        Munro right = new Munro("", 2, HillCategory.MUNRO, "");

        assertEquals(1, testSubject.compare(left, right), "Expect right (2) before left (1) when desc");
    }

    @Test
    public void comparesByHeightSameHeight(){
        Sort sort = Sort.by("height");
        MunroComparator testSubject = new MunroComparator(sort);
        Munro left = new Munro("", 1, HillCategory.MUNRO, "");
        Munro right = new Munro("", 1, HillCategory.MUNRO, "");

        assertEquals(0, testSubject.compare(left, right), "Expect both equal when same height");
    }

    @Test
    public void comparesByHeightThenName(){
        Sort sort = Sort.by("height", "name");
        MunroComparator testSubject = new MunroComparator(sort);
        Munro left = new Munro("B", 1, HillCategory.MUNRO, "");
        Munro right = new Munro("A", 2, HillCategory.MUNRO, "");

        assertEquals(-1, testSubject.compare(left, right), "Expect left (B,1) before right (A,2) when height first");
    }

    @Test
    public void comparesByNameThenHeight(){
        Sort sort = Sort.by("name", "height");
        MunroComparator testSubject = new MunroComparator(sort);
        Munro left = new Munro("B", 1, HillCategory.MUNRO, "");
        Munro right = new Munro("A", 2, HillCategory.MUNRO, "");

        assertEquals(1, testSubject.compare(left, right), "Expect right (A,2) before left (B,1) when name first");
    }

    @Test
    public void comparesByHeightThenNameHeightSame(){
        Sort sort = Sort.by("height", "name");
        MunroComparator testSubject = new MunroComparator(sort);
        Munro left = new Munro("B", 1, HillCategory.MUNRO, "");
        Munro right = new Munro("A", 1, HillCategory.MUNRO, "");

        assertEquals(1, testSubject.compare(left, right), "Expect right (A,2) before left (B,1) when height same");
    }

    @Test
    public void comparesByNameThenHeightNameSame(){
        Sort sort = Sort.by("name", "height");
        MunroComparator testSubject = new MunroComparator(sort);
        Munro left = new Munro("A", 1, HillCategory.MUNRO, "");
        Munro right = new Munro("A", 2, HillCategory.MUNRO, "");

        assertEquals(-1, testSubject.compare(left, right), "Expect left (B,1) before right (A,2) when name same");
    }

    @Test
    public void comparesByNameThenHeightNameSameHeightDesc(){
        Sort sort = Sort.by(Sort.Order.by("name"), Sort.Order.desc("height"));
        MunroComparator testSubject = new MunroComparator(sort);
        Munro left = new Munro("A", 1, HillCategory.MUNRO, "");
        Munro right = new Munro("A", 2, HillCategory.MUNRO, "");

        assertEquals(1, testSubject.compare(left, right), "Expect right (A,2) before left (B,1) when name same and height desc");
    }
}
