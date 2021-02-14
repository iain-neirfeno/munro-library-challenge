package com.neirfeno.munrolibrarychallenge.config;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;

import java.util.Map;

public class CustomContainerTests {

    @Test
    public void testConfiguresPortFromEnv(){
        Map<String, String> env = Map.of("PORT", "12345");
        ConfigurableServletWebServerFactory factory = Mockito.mock(ConfigurableServletWebServerFactory.class);
        CustomContainer testSubject = new CustomContainer(env);

        testSubject.customize(factory);

        Mockito.verify(factory).setPort(Mockito.eq(12345));
    }

    @Test
    public void testConfiguresPortToDefault8080(){
        Map<String, String> env = Map.of();
        ConfigurableServletWebServerFactory factory = Mockito.mock(ConfigurableServletWebServerFactory.class);
        CustomContainer testSubject = new CustomContainer(env);

        testSubject.customize(factory);

        Mockito.verify(factory).setPort(Mockito.eq(8080));
    }
}
