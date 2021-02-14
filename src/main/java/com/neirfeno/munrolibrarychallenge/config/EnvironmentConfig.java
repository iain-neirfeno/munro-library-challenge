package com.neirfeno.munrolibrarychallenge.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class EnvironmentConfig {

    @Bean(name = "environmentVariables")
    public Map<String, String> env(){
        return System.getenv();
    }
}
