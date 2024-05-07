package com.example.ibteam7.mapper;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.io.IOException;

/**
 * AttributeConverter implementation to convert JsonNode objects to String and vice versa.
 */
@Converter
public class JsonNodeConverter implements AttributeConverter<JsonNode, String> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Converts a JsonNode object to its string representation.
     * @param jsonNode The JsonNode object to convert.
     * @return String representation of the JsonNode.
     */
    @Override
    public String convertToDatabaseColumn(JsonNode jsonNode) {
        try {
            return objectMapper.writeValueAsString(jsonNode);
        } catch (IOException ex) {
            // Handle exception
            return null;
        }
    }

    /**
     * Converts a string to a JsonNode object.
     * @param jsonString The string to convert to a JsonNode.
     * @return JsonNode object.
     */
    @Override
    public JsonNode convertToEntityAttribute(String jsonString) {
        try {
            return objectMapper.readTree(jsonString);
        } catch (IOException ex) {
            // Handle exception
            return null;
        }
    }
}
