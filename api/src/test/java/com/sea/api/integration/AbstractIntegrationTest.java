package com.sea.api.integration;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.lifecycle.Startables;

@ContextConfiguration(initializers = AbstractIntegrationTest.Initializer.class)
public class AbstractIntegrationTest {
    
    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:17-alpine");

        @Override
        public void initialize(ConfigurableApplicationContext applicationContext) {
            startContainers();
            ConfigurableEnvironment environment = applicationContext.getEnvironment();
            Map<String, Object> config = createConnectionConfiguration();
            MapPropertySource propertySource = new MapPropertySource("testcontainers", config);
            environment.getPropertySources().addFirst(propertySource);
        }

        private static void startContainers(){
            Startables.deepStart(Stream.of(postgres)).join();
        }

        private static Map<String, Object> createConnectionConfiguration(){
            Map<String, Object> config = new HashMap<>();
            config.put("spring.datasource.url", postgres.getJdbcUrl());
            config.put("spring.datasource.username", postgres.getUsername());
            config.put("spring.datasource.password", postgres.getPassword());

            return config;
        }
    }
}
