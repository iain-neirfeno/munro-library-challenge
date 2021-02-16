package com.neirfeno.munrolibrarychallenge.rest;

import com.neirfeno.munrolibrarychallenge.rest.dto.MunroQuery;
import com.neirfeno.munrolibrarychallenge.rest.dto.OrderRequest;
import com.neirfeno.munrolibrarychallenge.rest.dto.PageRequest;
import com.neirfeno.munrolibrarychallenge.model.Munro;
import com.neirfeno.munrolibrarychallenge.model.MunroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
public class MunroController {

    private final MunroRepository repository;

    @Autowired
    public MunroController(MunroRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/munros")
    public ResponseEntity<List<Munro>> query(
            @Valid MunroQuery munroQuery,
            @Valid OrderRequest orderRequest,
            @Valid PageRequest pageRequest,
            HttpServletRequest request
            ) {

        Sort sort = orderRequest.asSort();
        Pageable pageable = pageRequest.asPageable(sort);

        Page<Munro> result = repository.findBy(pageable, munroQuery.getCategory(), munroQuery.getMinHeight(), munroQuery.getMaxHeight());

        return buildMunroResponse(request, result);
    }

    private static ResponseEntity<List<Munro>> buildMunroResponse(HttpServletRequest request, Page<Munro> munroPage){
        return munroPage.getPageable().isPaged() ? asPageResponse(munroPage, request) : ResponseEntity.ok(munroPage.getContent());
    }

    private static ResponseEntity<List<Munro>> asPageResponse(Page<Munro> page,  HttpServletRequest request){
        List<String> links = new ArrayList<>();
        String url = request.getRequestURL().toString();
        if (StringUtils.hasText(request.getQueryString()))
            url = url + "?" + request.getQueryString();
        var uriBuilder = UriComponentsBuilder.fromUriString(url);

        if (page.hasNext())
            links.add(linkValue("next", updatedUri(page.nextPageable(), uriBuilder)));

        links.add(linkValue("last", updatedUri(page, false, uriBuilder)));
        links.add(linkValue("first", updatedUri(page, true, uriBuilder)));

        if (page.hasPrevious())
            links.add(linkValue("prev", updatedUri(page.previousPageable(), uriBuilder)));

        return ResponseEntity.ok().header("Link", String.join(",", links)).body(page.getContent());
    }

    private static String linkValue(String rel, String uri){
        return String.format("<%s>; rel=\"%s\"", uri, rel);
    }

    private static String updatedUri(Pageable pageable, UriComponentsBuilder uriBuilder){
        return uriBuilder.replaceQueryParam("page", pageable.getPageNumber()).toUriString();
    }

    private static String updatedUri(Page<?> page, Boolean firstPage, UriComponentsBuilder uriBuilder){
        if (firstPage)
            return uriBuilder.replaceQueryParam("page", 0).toUriString();

        return uriBuilder.replaceQueryParam("page", page.getTotalPages() - 1).toUriString();
    }
}
