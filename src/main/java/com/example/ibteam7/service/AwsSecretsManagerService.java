package com.example.ibteam7.service;

import com.example.ibteam7.config.Constants;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueResponse;
import software.amazon.awssdk.services.secretsmanager.model.SecretsManagerException;

import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class AwsSecretsManagerService {

    private final SecretsManagerClient secretsManagerClient;
    private final ObjectMapper objectMapper;

    public Map<String,String> getSecrets(String secretName) {
        try {
            GetSecretValueRequest request = GetSecretValueRequest.builder()
                    .secretId(secretName)
                    .build();

            GetSecretValueResponse response = secretsManagerClient.getSecretValue(request);

            return objectMapper.readValue(response.secretString(), new TypeReference<>() {});

        } catch (SecretsManagerException | JsonProcessingException e) {
            log.error(e.getMessage());
            return Collections.emptyMap();
        }
    }

    public void setDatabaseCredentials() {

        Map<String,String> dbSecretsMap = getSecrets(Constants.RDS_SECRET_NAME);
        log.error(dbSecretsMap.toString());
        System.setProperty("db.host", dbSecretsMap.get("host"));
        System.setProperty("db.port", dbSecretsMap.get("port"));
        System.setProperty("db.dbname", dbSecretsMap.get("dbname"));
        System.setProperty("db.username", dbSecretsMap.get("username"));
        System.setProperty("db.password", dbSecretsMap.get("password"));

    }

}
