package com.neirfeno.munrolibrarychallenge.rest.dto;

import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Sort;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PageRequestTests {

    @Test
    public void testAsPageWhenNoLimitOrPageSizeProvidedReturnsUnpagedResult(){
        PageRequest testSubject = new PageRequest();

        assertTrue(testSubject.asPageable(Sort.unsorted()).isUnpaged());
    }

    @Test
    public void testAsPageWhenNoLimitOrPageSizeHasSortAsPassed(){
        PageRequest testSubject = new PageRequest();
        Sort sort = Sort.by("dave");

        assertEquals(sort, testSubject.asPageable(sort).getSort());
    }

    @Test
    public void testAsPageWhenOnlyPageProvidedReturnsUnpagedResult(){
        PageRequest testSubject = new PageRequest();
        testSubject.setPage(0);

        assertTrue(testSubject.asPageable(Sort.unsorted()).isUnpaged());
    }

    @Test
    public void testAsPageWhenOnlyLimitProvidedReturnsUnpagedResult(){
        PageRequest testSubject = new PageRequest();
        testSubject.setLimit(1);

        assertTrue(testSubject.asPageable(Sort.unsorted()).isUnpaged());
    }

    @Test
    public void testAsPageWhenLimitAndPageProvidedReturnsPagedResult(){
        PageRequest testSubject = new PageRequest();
        testSubject.setLimit(1);
        testSubject.setPage(0);

        assertTrue(testSubject.asPageable(Sort.unsorted()).isPaged());
    }

    @Test
    public void testAsPageWhenLimitAndPageProvidedHasSortAsPassed(){
        PageRequest testSubject = new PageRequest();
        testSubject.setLimit(1);
        testSubject.setPage(0);

        Sort sort = Sort.by("dave");

        assertEquals(sort, testSubject.asPageable(sort).getSort());
    }
}
