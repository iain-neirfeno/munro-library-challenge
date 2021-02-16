package com.neirfeno.munrolibrarychallenge.rest.validator;

import com.neirfeno.munrolibrarychallenge.rest.dto.OrderRequest;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class OrderValidatorTests {

    @Test
    public void testOrderVerifiesPropertyNamesValid(){
        OrderValidator testSubject = new OrderValidator();
        IsOrder annotation = Mockito.mock(IsOrder.class);
        Mockito.when(annotation.properties()).thenReturn(new String[]{"albert"});

        OrderRequest orderRequest = Mockito.mock(OrderRequest.class);
        Mockito.when(orderRequest.getPropertiesWithDirection()).thenReturn(
                Stream.of(
                        new OrderRequest.PropertyDirectionRequest("albert", null)
                ));

        testSubject.initialize(annotation);

        assertTrue(testSubject.isValid(orderRequest, null));
    }

    @Test
    public void testOrderVerifiesPropertyNamesInvalid(){
        OrderValidator testSubject = new OrderValidator();
        IsOrder annotation = Mockito.mock(IsOrder.class);
        Mockito.when(annotation.properties()).thenReturn(new String[]{"albert"});

        OrderRequest orderRequest = Mockito.mock(OrderRequest.class);
        Mockito.when(orderRequest.getPropertiesWithDirection()).thenReturn(
                Stream.of(
                        new OrderRequest.PropertyDirectionRequest("bert", null)
                ));

        testSubject.initialize(annotation);

        assertFalse(testSubject.isValid(orderRequest, null));
    }

    @Test
    public void testOrderVerifiesPropertyNamesIgnoresCase(){
        OrderValidator testSubject = new OrderValidator();
        IsOrder annotation = Mockito.mock(IsOrder.class);
        Mockito.when(annotation.properties()).thenReturn(new String[]{"albert"});

        OrderRequest orderRequest = Mockito.mock(OrderRequest.class);
        Mockito.when(orderRequest.getPropertiesWithDirection()).thenReturn(
                Stream.of(
                        new OrderRequest.PropertyDirectionRequest("AlBeRt", null)
                ));

        testSubject.initialize(annotation);

        assertTrue(testSubject.isValid(orderRequest, null));
    }

    @Test
    public void testOrderVerifiesDirectionValid(){
        OrderValidator testSubject = new OrderValidator();
        IsOrder annotation = Mockito.mock(IsOrder.class);
        Mockito.when(annotation.properties()).thenReturn(new String[]{"albert", "dave"});

        OrderRequest orderRequest = Mockito.mock(OrderRequest.class);
        Mockito.when(orderRequest.getPropertiesWithDirection()).thenReturn(
                Stream.of(
                        new OrderRequest.PropertyDirectionRequest("albert", "asc"),
                        new OrderRequest.PropertyDirectionRequest("dave", "desc")
                ));

        testSubject.initialize(annotation);

        assertTrue(testSubject.isValid(orderRequest, null));
    }

    @Test
    public void testOrderVerifiesDirectionInvalid(){
        OrderValidator testSubject = new OrderValidator();
        IsOrder annotation = Mockito.mock(IsOrder.class);
        Mockito.when(annotation.properties()).thenReturn(new String[]{"albert"});

        OrderRequest orderRequest = Mockito.mock(OrderRequest.class);
        Mockito.when(orderRequest.getPropertiesWithDirection()).thenReturn(
                Stream.of(
                        new OrderRequest.PropertyDirectionRequest("albert", "bert")
                ));

        testSubject.initialize(annotation);

        assertFalse(testSubject.isValid(orderRequest, null));
    }

    @Test
    public void testOrderVerifiesDirectionIgnoresDirectionCase(){
        OrderValidator testSubject = new OrderValidator();
        IsOrder annotation = Mockito.mock(IsOrder.class);
        Mockito.when(annotation.properties()).thenReturn(new String[]{"albert"});

        OrderRequest orderRequest = Mockito.mock(OrderRequest.class);
        Mockito.when(orderRequest.getPropertiesWithDirection()).thenReturn(
                Stream.of(
                        new OrderRequest.PropertyDirectionRequest("albert", "AsC")
                ));

        testSubject.initialize(annotation);

        assertTrue(testSubject.isValid(orderRequest, null));
    }
}
