package com.neirfeno.munrolibrarychallenge.model;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MunroRepository {

    Page<Munro> findBy(Pageable pageable, HillCategory category, Integer minHeight, Integer maxHeight);
}
