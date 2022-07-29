package com.verma.keygenerator.config;

import com.verma.keygenerator.generators.KeyGenerator;
import com.verma.keygenerator.generators.KeyGeneratorFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class KeyGeneratorConfig {

    @Value("${key-generator.node.id}")
    private String nodeId;

    @Value("${key-generator.name}")
    private String generator;

    @Bean
    public KeyGenerator keyGenerator() {
        KeyGenerator keyGenerator = KeyGeneratorFactory.builder()
                .withGenerator(generator)
                .withNodeId(Integer.parseInt(nodeId))
                .load()
                .generator();
        log.info("Loading KeyGenerator {}", keyGenerator.getClass().getSimpleName());
        return keyGenerator;
    }
}
