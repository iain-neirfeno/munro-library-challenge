package com.neirfeno.munrolibrarychallenge.repository;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

public class UnpagedRequest extends PageRequest {

    public UnpagedRequest(Sort sort) {
        super(1, 1, sort);
    }

    @Override
    public boolean isPaged() {
        return false;
    }
}
