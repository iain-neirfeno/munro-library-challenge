package com.neirfeno.munrolibrarychallenge.rest.dto;

import com.neirfeno.munrolibrarychallenge.repository.UnpagedRequest;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Getter
@Setter
public class PageRequest {
    private Integer page;
    private Integer limit;

    public Pageable asPageable(Sort sort){
        if (page == null || limit == null)
            return new UnpagedRequest(sort);

        return org.springframework.data.domain.PageRequest.of(page, limit, sort);
    }
}
