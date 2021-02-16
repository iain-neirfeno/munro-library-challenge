package com.neirfeno.munrolibrarychallenge.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
@Setter
public class OrderRequest {
    private String order;

    public Sort asSort(){
        if (order == null)
            return Sort.unsorted();

        return Sort.by(getPropertiesWithDirection().map(p -> {
            Sort.Order sortOrder = Sort.Order.by(p.property);
            if (StringUtils.hasText(p.direction))
                sortOrder = sortOrder.with("desc".equalsIgnoreCase(p.direction) ? Sort.Direction.DESC : Sort.Direction.ASC);
            return sortOrder;
        }).collect(Collectors.toUnmodifiableList()));
    }

    public Stream<PropertyDirectionRequest> getPropertiesWithDirection(){
        if (!StringUtils.hasText(order))
            return Stream.<PropertyDirectionRequest>builder().build();

        return Arrays.stream(order.split(",")).map(str -> {
            String[] property_and_direction = str.split(";", 2);
            String property = property_and_direction[0];
            if (!StringUtils.hasText(property))
                return null;

            String direction = null;
            if(property_and_direction.length > 1 && StringUtils.hasText(property_and_direction[1]))
                direction = property_and_direction[1];

            return new PropertyDirectionRequest(property, direction);
        }).filter(Objects::nonNull);
    }

    @AllArgsConstructor
    @Getter
    public static class PropertyDirectionRequest {
        private final String property;
        private final String direction;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            PropertyDirectionRequest that = (PropertyDirectionRequest) o;
            return Objects.equals(property, that.property) && Objects.equals(direction, that.direction);
        }

        @Override
        public int hashCode() {
            return Objects.hash(property, direction);
        }
    }
}
