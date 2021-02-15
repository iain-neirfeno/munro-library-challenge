package com.neirfeno.munrolibrarychallenge.repository;

import com.neirfeno.munrolibrarychallenge.model.HillCategory;
import com.neirfeno.munrolibrarychallenge.model.Munro;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.util.NumberUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class MunroDataSource {

    private final String munroDataPath;
    private final String munroDataHeaderName;
    private final String munroDataHeaderHeight;
    private final String munroDataHeaderCategory;
    private final String munroDataHeaderGridReference;

    public MunroDataSource(
            @Value("${com.neirfeno.munrolibrarychallenge.datasource.csv}")
            String munroDataPath,
            @Value("${com.neirfeno.munrolibrarychallenge.datasource.header.name}")
            String munroDataHeaderName,
            @Value("${com.neirfeno.munrolibrarychallenge.datasource.header.height}")
            String munroDataHeaderHeight,
            @Value("${com.neirfeno.munrolibrarychallenge.datasource.header.category}")
            String munroDataHeaderCategory,
            @Value("${com.neirfeno.munrolibrarychallenge.datasource.header.gridref}")
            String munroDataHeaderGridReference) {
        this.munroDataPath = munroDataPath;
        this.munroDataHeaderName = munroDataHeaderName;
        this.munroDataHeaderHeight = munroDataHeaderHeight;
        this.munroDataHeaderCategory = munroDataHeaderCategory;
        this.munroDataHeaderGridReference = munroDataHeaderGridReference;
    }

    @Bean
    public Resource munroData() {
        return new ClassPathResource(munroDataPath);
    }

    @Bean
    public List<Munro> munros(Resource munroData) throws IOException {
        CSVParser csvParser = CSVParser.parse(munroData.getInputStream(), StandardCharsets.UTF_8, CSVFormat.DEFAULT.withFirstRecordAsHeader());
        return StreamSupport.stream(csvParser.spliterator(), true)
                .filter(this::isRecordValid)
                .map(this::recordToMunro)
                .collect(Collectors.toUnmodifiableList());
    }

    private Munro recordToMunro(CSVRecord record){
        return new Munro(
                record.get(munroDataHeaderName),
                NumberUtils.parseNumber(record.get(munroDataHeaderHeight), Integer.class),
                record.get(munroDataHeaderCategory).equalsIgnoreCase("mun") ? HillCategory.MUNRO : HillCategory.TOP,
                record.get(munroDataHeaderGridReference));
    }

    private Boolean isRecordValid(CSVRecord record){
        return !record.get(munroDataHeaderName).isBlank() &&
                !record.get(munroDataHeaderHeight).isBlank() &&
                record.get(munroDataHeaderHeight).chars().allMatch(Character::isDigit) &&
                (record.get(munroDataHeaderCategory).equalsIgnoreCase("mun") || record.get(munroDataHeaderCategory).equalsIgnoreCase("top")) &&
                !record.get(munroDataHeaderGridReference).isBlank();


    }
}
