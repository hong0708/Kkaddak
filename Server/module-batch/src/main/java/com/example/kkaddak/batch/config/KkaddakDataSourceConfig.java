package com.example.kkaddak.batch.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;

@Configuration
@PropertySource({"classpath:application.yml"})
@EnableJpaRepositories(
        basePackages = "com.example.kkaddak.core.repository",
        entityManagerFactoryRef = "kkaddakEntityManager",
        transactionManagerRef = "kkaddakTransactionManager"
)
@ConditionalOnMissingBean(name = "jpaAuditingHandler")
public class KkaddakDataSourceConfig {

    @Bean
    public LocalContainerEntityManagerFactoryBean kkaddakEntityManager() {
        final LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(kkaddakDataSource());
        em.setPackagesToScan("com.example.kkaddak.core.entity");
        em.setPersistenceUnitName("kkaddakEntityManager");

        final HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        HashMap<String, String> properties = new HashMap<>();
        properties.put("hibernate.ddl-auto", ddl);
        properties.put("hibernate.dialect", "org.hibernate.dialect.MySQL5InnoDBDialect");
        properties.put("hibernate.implicit_naming_strategy",
                "org.hibernate.boot.model.naming.ImplicitNamingStrategyJpaCompliantImpl");
        properties.put("hibernate.physical_naming_strategy",
                "com.example.kkaddak.core.utils.SnakeCasePhysicalNamingStrategy");
        em.setJpaPropertyMap(properties);

        return em;
    }

    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String ddl;

    @Bean
    @ConfigurationProperties(prefix="spring.coredb.datasource")
    public DataSource kkaddakDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    public PlatformTransactionManager kkaddakTransactionManager() {
        final JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(kkaddakEntityManager().getObject());
        return transactionManager;
    }
}