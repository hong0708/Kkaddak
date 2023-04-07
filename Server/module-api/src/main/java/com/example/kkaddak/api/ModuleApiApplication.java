package com.example.kkaddak.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@ComponentScan("com.example.kkaddak.core")
@ComponentScan("com.example.kkaddak.api")
@EnableJpaRepositories("com.example.kkaddak.core")
@EntityScan({"com.example.kkaddak.core", "com.example.kkaddak.api"})
@SpringBootApplication(scanBasePackages = {"com.example.kkaddak"}, exclude = SecurityAutoConfiguration.class)
public class ModuleApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ModuleApiApplication.class, args);
    }

}
