package com.neirfeno.munrolibrarychallenge;

import com.neirfeno.munrolibrarychallenge.model.HillCategory;
import com.neirfeno.munrolibrarychallenge.model.Munro;
import com.neirfeno.munrolibrarychallenge.model.MunroRepository;
import com.neirfeno.munrolibrarychallenge.repository.UnpagedRequest;
import com.neirfeno.munrolibrarychallenge.rest.MunroController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MunroController.class)
public class WebMockMunroTests {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private MunroRepository repo;

    @Test
    public void testGetAllUnpaged() throws Exception {

        when(repo.findBy(argThat(Pageable::isUnpaged), isNull(), isNull(), isNull())).thenReturn(new MunroPageBuilder()
                .add("albert", 1000, HillCategory.MUNRO, "NN0001").build(Pageable.unpaged()));

        mvc.perform(get("/munros")).andDo(print()).andExpect(status().isOk()).andExpect(
                content().json("[{'name':'albert','height':1000,'hillCategory':'MUNRO','gridReference':'NN0001'}]"));
    }

    @Test
    public void testGetCanSortUnpaged() throws Exception {
        Sort sort = Sort.by(Sort.Direction.DESC, "name");
        when(repo.findBy(argThat(p -> p.isUnpaged()
                        && asString(p.getSort()).equals(asString(sort))),
                isNull(), isNull(), isNull())).thenReturn(new MunroPageBuilder()
                .add("albert", 1000, HillCategory.MUNRO, "NN0001").build(new UnpagedRequest(sort)));

        mvc.perform(get("/munros?order=name;desc")).andDo(print()).andExpect(status().isOk()).andExpect(
                content().json("[{'name':'albert','height':1000,'hillCategory':'MUNRO','gridReference':'NN0001'}]"));
    }

    @Test
    public void testGetAllPaged() throws Exception {

        when(repo.findBy(
                argThat(p -> p.isPaged() && p.getPageNumber() == 1 && p.getPageSize() == 1), isNull(), isNull(), isNull()))
                .thenReturn(new MunroPageBuilder()
                        .add("albert", 1000, HillCategory.MUNRO, "NN0001")
                        .size(3)
                        .build(PageRequest.of(1, 1)));

        mvc.perform(get("/munros?page=1&limit=1")).andDo(print()).andExpect(status().isOk()).andExpect(
                content().json("[{'name':'albert','height':1000,'hillCategory':'MUNRO','gridReference':'NN0001'}]"))
                .andExpect(header().string("Link", "<http://localhost/munros?limit=1&page=2>; rel=\"next\"," +
                        "<http://localhost/munros?limit=1&page=2>; rel=\"last\"," +
                        "<http://localhost/munros?limit=1&page=0>; rel=\"first\"," +
                        "<http://localhost/munros?limit=1&page=0>; rel=\"prev\""));
    }

    @Test
    public void testGetCanSortPaged() throws Exception {
        Sort sort = Sort.by(Sort.Direction.DESC, "name");
        when(repo.findBy(argThat(p -> p.isPaged() && p.getPageSize() == 1 && p.getPageNumber() == 1
                        && asString(p.getSort()).equals(asString(sort))),
                isNull(), isNull(), isNull())).thenReturn(new MunroPageBuilder()
                .add("albert", 1000, HillCategory.MUNRO, "NN0001").build(new UnpagedRequest(sort)));

        mvc.perform(get("/munros?order=name;desc&page=1&limit=1")).andDo(print()).andExpect(status().isOk()).andExpect(
                content().json("[{'name':'albert','height':1000,'hillCategory':'MUNRO','gridReference':'NN0001'}]"));
    }

    @Test
    public void testGetAllPagedWhenPageIsFirstNoPrev() throws Exception {

        when(repo.findBy(
                argThat(p -> p.isPaged() && p.getPageNumber() == 0 && p.getPageSize() == 1), isNull(), isNull(), isNull()))
                .thenReturn(new MunroPageBuilder()
                        .add("albert", 1000, HillCategory.MUNRO, "NN0001")
                        .size(3)
                        .build(PageRequest.of(0, 1)));

        mvc.perform(get("/munros?page=0&limit=1")).andDo(print()).andExpect(status().isOk()).andExpect(
                content().json("[{'name':'albert','height':1000,'hillCategory':'MUNRO','gridReference':'NN0001'}]"))
                .andExpect(header().string("Link", "<http://localhost/munros?limit=1&page=1>; rel=\"next\"," +
                        "<http://localhost/munros?limit=1&page=2>; rel=\"last\"," +
                        "<http://localhost/munros?limit=1&page=0>; rel=\"first\""));
    }

    @Test
    public void testGetAllPagedWhenPageIsLastNoNext() throws Exception {

        when(repo.findBy(
                argThat(p -> p.isPaged() && p.getPageNumber() == 2 && p.getPageSize() == 1), isNull(), isNull(), isNull()))
                .thenReturn(new MunroPageBuilder()
                        .add("albert", 1000, HillCategory.MUNRO, "NN0001")
                        .size(3)
                        .build(PageRequest.of(2, 1)));

        mvc.perform(get("/munros?page=2&limit=1")).andDo(print()).andExpect(status().isOk()).andExpect(
                content().json("[{'name':'albert','height':1000,'hillCategory':'MUNRO','gridReference':'NN0001'}]"))
                .andExpect(header().string("Link", "<http://localhost/munros?limit=1&page=2>; rel=\"last\"," +
                        "<http://localhost/munros?limit=1&page=0>; rel=\"first\"," +
                        "<http://localhost/munros?limit=1&page=1>; rel=\"prev\""));
    }

    @Test
    public void testGetAllPagedWhenPageSizeOfFullResultsHasNoPrevOrNext() throws Exception {

        when(repo.findBy(
                argThat(p -> p.isPaged() && p.getPageNumber() == 0 && p.getPageSize() == 1), isNull(), isNull(), isNull()))
                .thenReturn(new MunroPageBuilder()
                        .add("albert", 1000, HillCategory.MUNRO, "NN0001")
                        .build(PageRequest.of(0, 1)));

        mvc.perform(get("/munros?page=0&limit=1")).andDo(print()).andExpect(status().isOk()).andExpect(
                content().json("[{'name':'albert','height':1000,'hillCategory':'MUNRO','gridReference':'NN0001'}]"))
                .andExpect(header().string("Link", "<http://localhost/munros?limit=1&page=0>; rel=\"last\"," +
                        "<http://localhost/munros?limit=1&page=0>; rel=\"first\""));
    }

    @Test
    public void testGetPagedMaintainsQueryInLinks() throws Exception {

        when(repo.findBy(
                argThat(p -> p.isPaged() && p.getPageNumber() == 0 && p.getPageSize() == 1), eq(HillCategory.MUNRO), eq(100), eq(2000)))
                .thenReturn(new MunroPageBuilder()
                        .add("albert", 1000, HillCategory.MUNRO, "NN0001")
                        .build(PageRequest.of(0, 1)));

        mvc.perform(get("/munros?page=0&limit=1&minHeight=100&maxHeight=2000&category=MUNRO&order=name;desc"))
                .andDo(print()).andExpect(status().isOk()).andExpect(
                content().json("[{'name':'albert','height':1000,'hillCategory':'MUNRO','gridReference':'NN0001'}]"))
                .andExpect(header().string("Link", "<http://localhost/munros?limit=1&minHeight=100&maxHeight=2000&category=MUNRO&order=name;desc&page=0>; rel=\"last\"," +
                        "<http://localhost/munros?limit=1&minHeight=100&maxHeight=2000&category=MUNRO&order=name;desc&page=0>; rel=\"first\""));
    }

    @Test
    public void testGetByCategory() throws Exception {

        when(repo.findBy(
                argThat(Pageable::isUnpaged), eq(HillCategory.MUNRO), isNull(), isNull()))
                .thenReturn(new MunroPageBuilder()
                        .add("albert", 1000, HillCategory.MUNRO, "NN0001").build(Pageable.unpaged()));

        mvc.perform(get("/munros?category=MUNRO")).andDo(print()).andExpect(status().isOk()).andExpect(
                content().json("[{'name':'albert','height':1000,'hillCategory':'MUNRO','gridReference':'NN0001'}]"));
    }

    @Test
    public void testGetByCategoryIgnoreCase() throws Exception {

        when(repo.findBy(
                argThat(Pageable::isUnpaged), eq(HillCategory.MUNRO), isNull(), isNull()))
                .thenReturn(new MunroPageBuilder()
                        .add("albert", 1000, HillCategory.MUNRO, "NN0001").build(Pageable.unpaged()));

        mvc.perform(get("/munros?category=MuNrO")).andDo(print()).andExpect(status().isOk()).andExpect(
                content().json("[{'name':'albert','height':1000,'hillCategory':'MUNRO','gridReference':'NN0001'}]"));
    }

    @Test
    public void testGetByCategorySpecifyEither() throws Exception {

        when(repo.findBy(
                argThat(Pageable::isUnpaged), isNull(), isNull(), isNull()))
                .thenReturn(new MunroPageBuilder()
                        .add("albert", 1000, HillCategory.MUNRO, "NN0001").build(Pageable.unpaged()));

        mvc.perform(get("/munros?category=either")).andDo(print()).andExpect(status().isOk()).andExpect(
                content().json("[{'name':'albert','height':1000,'hillCategory':'MUNRO','gridReference':'NN0001'}]"));
    }

    @Test
    public void testGetByMinHeight() throws Exception {

        when(repo.findBy(
                argThat(Pageable::isUnpaged), isNull(), eq(100), isNull()))
                .thenReturn(new MunroPageBuilder()
                        .add("albert", 1000, HillCategory.MUNRO, "NN0001").build(Pageable.unpaged()));

        mvc.perform(get("/munros?minHeight=100")).andDo(print()).andExpect(status().isOk()).andExpect(
                content().json("[{'name':'albert','height':1000,'hillCategory':'MUNRO','gridReference':'NN0001'}]"));
    }

    @Test
    public void testGetByMaxHeight() throws Exception {

        when(repo.findBy(
                argThat(Pageable::isUnpaged), isNull(), isNull(), eq(100)))
                .thenReturn(new MunroPageBuilder()
                        .add("albert", 1000, HillCategory.MUNRO, "NN0001").build(Pageable.unpaged()));

        mvc.perform(get("/munros?maxHeight=100")).andDo(print()).andExpect(status().isOk()).andExpect(
                content().json("[{'name':'albert','height':1000,'hillCategory':'MUNRO','gridReference':'NN0001'}]"));
    }

    @Test
    public void testGetByCategoryNameInvalidReturns400() throws Exception {
        mvc.perform(get("/munros?category=100")).andDo(print()).andExpect(status().isBadRequest());
    }

    @Test
    public void testGetByMinHeightInvalidReturns400() throws Exception {
        mvc.perform(get("/munros?minHeight=abc")).andDo(print()).andExpect(status().isBadRequest());
    }

    @Test
    public void testGetByMaxHeightInvalidReturns400() throws Exception {
        mvc.perform(get("/munros?maxHeight=abc")).andDo(print()).andExpect(status().isBadRequest());
    }

    @Test
    public void testGetByMinHeightLargerThanMaxHeightReturns400() throws Exception {
        mvc.perform(get("/munros?maxHeight=100&minHeight=500")).andDo(print()).andExpect(status().isBadRequest());
    }



    private static List<String> asString(Sort sort){
        return sort.stream().map(o -> String.format("%s;%s", o.getProperty(), o.getDirection())).collect(Collectors.toUnmodifiableList());
    }

    private static class MunroPageBuilder{

        private final List<Munro> munros = new ArrayList<>();
        private Integer size;

        public MunroPageBuilder add(String name, int height, HillCategory category, String gridRef){
            munros.add(new Munro(name, height, category, gridRef));
            return this;
        }

        public MunroPageBuilder size(int totalSize){
            size = totalSize;
            return this;
        }

        public Page<Munro> build(Pageable pageable) {
            return new PageImpl<>(munros, pageable, size != null ? size : munros.size());
        }
    }
}
