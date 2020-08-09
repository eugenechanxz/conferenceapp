package com.pluralsight.conferencedemo.config;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class PersistenceConfiguration {
    //returns datasource object
    @Bean //we specified it's bean. Spring will look for that bean in the Spring context and replace the DataSource definition with what we've implemented here
    public DataSource dataSource() {
        DataSourceBuilder builder = DataSourceBuilder.create();
        builder.url("jdbc:postgresql://localhost:5432/conference_app");
        builder.username("postgres");
        builder.password("Welcome");
        System.out.println("My custom datasource bean has been initialized and set");
        return builder.build();
    }
}
