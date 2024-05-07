package com.example.ibteam7.dto;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@Data
@NoArgsConstructor
public class ConfigDto {
    private String tenantId;
    private String bannerImageUrl;
    private Integer defaultPropertyId;
    private String defaultPropertyName;
    private JsonNode propertyConfig;
}
