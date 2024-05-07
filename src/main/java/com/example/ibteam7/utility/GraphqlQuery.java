package com.example.ibteam7.utility;

import com.example.ibteam7.config.Constants;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * Utility class to execute GraphQL queries using RestTemplate and HttpHeaders
 * to set the API key
 * and query in the request body
 * The API key is fetched from the environment variables
 */
@Component
public class GraphqlQuery {
//     @Value("${apiKey}")
//     private String apiKey;
    // =System.getenv("GRAPHQL_API_KEY");

    @Autowired
    private Environment environment;

    RestTemplate restTemplate = new RestTemplate();

    /**
     * @param query GraphQL query to be executed
     * @return ResponseEntity with the response body
     *         Executes the GraphQL query by setting the API key in the headers and
     *         the query in the request body
     *         The response body is returned as a ResponseEntity
     */
    public ResponseEntity<JsonNode> executeGraphQLQueryHelper(String query) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
//        String apiKey = environment.getProperty("GRAPHQL_API_KEY");

        String apiKey = "da2-j3m6jevvlrfrpn4n2c7dkvpuau";
        headers.set("x-api-key", apiKey);
        System.out.println("API KEY: " + apiKey);
        System.out.println("Query: " + query);
        String requestBody = "{ \"query\": \"" + query + "\" }";

        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
        String url = Constants.GRAPHQL_URL;
        return restTemplate.exchange(url, HttpMethod.POST, requestEntity, JsonNode.class);
    }
}
