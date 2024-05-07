package com.example.ibteam7.entity;

import com.example.ibteam7.mapper.JsonNodeConverter;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@Data
@NoArgsConstructor
public class ConfigEntity {

    @Id
    private String tenantId;
    private String bannerImageUrl;
    private Integer defaultPropertyId;
    private String defaultPropertyName;

    @Convert(converter = JsonNodeConverter.class)
    private JsonNode propertyConfig;


}
