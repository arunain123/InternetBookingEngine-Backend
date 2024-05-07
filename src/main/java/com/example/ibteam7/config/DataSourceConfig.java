package com.example.ibteam7.config;

import com.example.ibteam7.service.AwsSecretsManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@RequiredArgsConstructor
public class DataSourceConfig {

    private final AwsSecretsManagerService awsSecretsManagerService;

    @Bean
    public DataSource createDataSource() {
        awsSecretsManagerService.setDatabaseCredentials();
        return DataSourceBuilder
                .create()
                .url("jdbc:postgresql://" + System.getProperty("db.host") + ":" + System.getProperty("db.port") + "/" + System.getProperty("db.dbname"))
                .username(System.getProperty("db.username"))
                .password(System.getProperty("db.password"))
                .build();
    }
}
