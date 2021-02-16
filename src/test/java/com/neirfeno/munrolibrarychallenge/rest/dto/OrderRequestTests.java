package com.neirfeno.munrolibrarychallenge.rest.dto;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderRequestTests {

    @Test
    public void testPropertiesWithDirectionNotProvidedResultsInEmptySort(){
        OrderRequest testSubject = new OrderRequest();

        assertEquals(0, testSubject.getPropertiesWithDirection().count());
    }

    @Test
    public void testPropertiesWithDirectionBlankResultsInEmptySort(){
        OrderRequest testSubject = new OrderRequest();
        testSubject.setOrder(" ");

        assertEquals(0, testSubject.getPropertiesWithDirection().count());
    }

    @Test
    public void testPropertiesWithDirectionSingleResultsInDefaultDirection(){
        OrderRequest testSubject = new OrderRequest();
        testSubject.setOrder("name");

        assertEquals(
                new PropertyDirectionBuilder().add("name").build(),
                testSubject.getPropertiesWithDirection().collect(Collectors.toUnmodifiableList()));
    }

    @Test
    public void testPropertiesWithDirectionSingleWithDirectionResultsInSpecifiedDirection(){
        OrderRequest testSubject = new OrderRequest();
        testSubject.setOrder("name;desc");

        assertEquals(
                new PropertyDirectionBuilder().add("name", "desc").build(),
                testSubject.getPropertiesWithDirection().collect(Collectors.toUnmodifiableList()));
    }

    @Test
    public void testPropertiesWithDirectionCanSpecifyMultipleInOrder(){
        OrderRequest testSubject = new OrderRequest();
        testSubject.setOrder("name;desc,other,dave;asc");

        assertEquals(
                new PropertyDirectionBuilder().add("name", "desc")
                        .add("other")
                        .add("dave", "asc").build(),
                testSubject.getPropertiesWithDirection().collect(Collectors.toUnmodifiableList()));
    }

    private static class PropertyDirectionBuilder {
        private final List<OrderRequest.PropertyDirectionRequest> list = new ArrayList<>();

        public PropertyDirectionBuilder add(String property){
            return add(property, null);
        }

        public PropertyDirectionBuilder add(String property, String direction){
            list.add(new OrderRequest.PropertyDirectionRequest(property, direction));
            return this;
        }

        public List<OrderRequest.PropertyDirectionRequest> build() {
            return list;
        }
    }
}
