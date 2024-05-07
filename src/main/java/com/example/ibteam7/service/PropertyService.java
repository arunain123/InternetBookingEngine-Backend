package com.example.ibteam7.service;

import com.example.ibteam7.config.Constants;
import com.example.ibteam7.utility.GraphqlQuery;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * Service class for managing properties.
 */
@Service
public class PropertyService {

    private static final String QUERY = "{ listProperties { property_id, property_name } }";

    @Autowired
    public GraphqlQuery graphqlQuery;

    /**
     * Retrieves all hotels from the GraphQL API.
     * @return ResponseEntity containing the list of hotels as JSON.
     */
    public ResponseEntity<JsonNode> findAllHotels() {
        return graphqlQuery.executeGraphQLQueryHelper(QUERY);
    }
}
