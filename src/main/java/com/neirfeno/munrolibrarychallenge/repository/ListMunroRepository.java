package com.neirfeno.munrolibrarychallenge.repository;

import com.neirfeno.munrolibrarychallenge.model.HillCategory;
import com.neirfeno.munrolibrarychallenge.model.Munro;
import com.neirfeno.munrolibrarychallenge.model.MunroRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class ListMunroRepository implements MunroRepository {

    private final List<Munro> cache;

    public ListMunroRepository(List<Munro> munroData){
        this.cache = munroData;
    }

    @Override
    public Page<Munro> findBy(Pageable pageable, HillCategory category, Integer minHeight, Integer maxHeight) {
        List<Munro> data = cache.parallelStream()
                .filter(munro -> category == null || munro.getHillCategory() == category)
                .filter(munro -> minHeight == null || munro.getHeight() >= minHeight)
                .filter(munro -> maxHeight == null || munro.getHeight() <= maxHeight)
                .sorted(new MunroComparator(pageable.getSort())).collect(Collectors.toUnmodifiableList());

        return new PageImpl<>(
                pageable.isPaged() ?
                        data.stream()
                                .skip(pageable.getOffset())
                                .limit(pageable.getPageSize())
                                .collect(Collectors.toUnmodifiableList()) :
                        data, pageable, data.size());
    }
}
