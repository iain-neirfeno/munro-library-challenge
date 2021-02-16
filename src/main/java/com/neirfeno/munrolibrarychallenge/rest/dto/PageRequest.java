package com.neirfeno.munrolibrarychallenge.rest.dto;

import com.neirfeno.munrolibrarychallenge.repository.UnpagedRequest;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.validation.constraints.Min;

@Getter
@Setter
public class PageRequest {
    @Min(value = 0, message = "page number cannot be less than 0")
    private Integer page;
    @Min(value = 1, message = "page limit cannot be less than 1")
    private Integer limit;

    public Pageable asPageable(Sort sort){
        if (page == null || limit == null)
            return new UnpagedRequest(sort);

        return org.springframework.data.domain.PageRequest.of(page, limit, sort);
    }
}
