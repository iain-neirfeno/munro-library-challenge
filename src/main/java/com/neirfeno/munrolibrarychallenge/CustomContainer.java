package com.neirfeno.munrolibrarychallenge;

import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.NumberUtils;
import org.springframework.util.StringUtils;

@Component
public class CustomContainer implements
        WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> {

    public void customize(ConfigurableServletWebServerFactory factory) {
        String preferredPort = System.getenv("PORT");
        int port = 8080;
        if (StringUtils.hasText(preferredPort) && preferredPort.chars().allMatch(Character::isDigit))
            port = NumberUtils.parseNumber(preferredPort, Integer.class);
        factory.setPort(port);
    }
}
