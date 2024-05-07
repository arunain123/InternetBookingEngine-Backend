package com.example.ibteam7.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;

/**
 * Configuration class for AWS Secrets Manager.
 * This class provides a bean for SecretsManagerClient.
 * This bean is used to get secrets from AWS Secrets Manager.
 */
@Configuration
public class AwsSecretsManagerConfig {

    /**
     * @return SecretsManagerClient
     * This method returns a SecretsManagerClient object.
     * This object is used to get secrets from AWS Secrets Manager.
     */
    @Bean
    public SecretsManagerClient secretsManagerClient() {
        AwsCredentialsProvider credentialsProvider = DefaultCredentialsProvider.create();

        return SecretsManagerClient.builder()
                .credentialsProvider(credentialsProvider)
                .region(Region.AP_NORTHEAST_1)
                .build();
    }
}