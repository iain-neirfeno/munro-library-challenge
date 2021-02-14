package com.neirfeno.munrolibrarychallenge.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.NumberUtils;
import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.Optional;

@Component
public class CustomContainer implements
        WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> {

    private final Map<String, String> env;

    public CustomContainer(@Qualifier("environmentVariables") Map<String, String> env){
        this.env = env;
    }

    public void customize(ConfigurableServletWebServerFactory factory) {
        factory.setPort(preferredPort().orElse(8080));
    }

    private Optional<Integer> preferredPort(){
        String preferredPort = env.get("PORT");
        if (StringUtils.hasText(preferredPort) && preferredPort.chars().allMatch(Character::isDigit))
            return Optional.of(NumberUtils.parseNumber(preferredPort, Integer.class));
        return Optional.empty();
    }
}
