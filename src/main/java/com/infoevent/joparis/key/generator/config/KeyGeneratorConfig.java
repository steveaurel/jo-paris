package com.infoevent.joparis.key.generator.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.security.SecureRandom;
import java.util.Base64;

@Configuration
public class KeyGeneratorConfig {

    @Bean
    public SecureRandom secureRandom() {
        return new SecureRandom();
    }

    @Bean
    public Base64.Encoder base64Encoder() {
        return Base64.getUrlEncoder();
    }
}
