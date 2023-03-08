package com.example.kkaddak.batch;

import com.example.kkaddak.batch.config.BatchDataSourceConfig;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication(scanBasePackages = {"com.example.kkaddak.batch", "com.example.kkaddak.core"})
@EnableScheduling
@EnableJpaAuditing
@EnableBatchProcessing
@ConditionalOnMissingBean(name = "jpaAuditingHandler")
@ComponentScan("com.example.kkaddak.core")
@ComponentScan("com.example.kkaddak.batch")
public class ModuleBatchApplication {

    public static void main(String[] args) {
        SpringApplication.run(ModuleBatchApplication.class, args);
    }

}
