package com.btw.spring.jdbc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan
@EnableTransactionManagement
public class AppSpringConfig {

    @Bean
    public DriverManagerDataSource dataSource() {
        return new DriverManagerDataSource("jdbc:h2:./db_recipe", "sa","");
    }
}
