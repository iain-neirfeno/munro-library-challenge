package com.neirfeno.munrolibrarychallenge.repository;

import com.neirfeno.munrolibrarychallenge.model.Munro;
import org.springframework.data.domain.Sort;

import java.util.Comparator;
import java.util.Iterator;

public class MunroComparator implements Comparator<Munro> {

    private final Sort sort;

    public MunroComparator(Sort sort){
        this.sort = sort;
    }

    @Override
    public int compare(Munro left, Munro right) {
        int result = 0;
        Iterator<Sort.Order> sortOrder = sort.iterator();
        while (result == 0 && sortOrder.hasNext()){
            Sort.Order order = sortOrder.next();

            if ("name".equalsIgnoreCase(order.getProperty())) {
                result = compare(order, left.getName(), right.getName());
            } else {
                throw new IllegalArgumentException(String.format("Unknown sort property %s", order.getProperty()));
            }
        }
        return result;
    }

    private int compare(Sort.Order order, String left, String right){
        int inversionOrderFactor = order.isAscending() ? 1 : -1;

        if (left == null && right == null)
            return 0;

        if (left == null)
            return switch (order.getNullHandling()) {
                case NATIVE -> inversionOrderFactor;
                case NULLS_FIRST -> -1;
                case NULLS_LAST -> 1;
            };

        if (right == null)
            return switch (order.getNullHandling()) {
                case NATIVE -> inversionOrderFactor * -1;
                case NULLS_FIRST -> 1;
                case NULLS_LAST -> -1;
            };

        int leftComparedWithRight = order.isIgnoreCase() ? left.compareToIgnoreCase(right) : left.compareTo(right);

        if (leftComparedWithRight == 0)
            return 0;

        return leftComparedWithRight > 0 ? inversionOrderFactor : -1 * inversionOrderFactor;
    }
}
