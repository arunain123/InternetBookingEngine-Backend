package com.example.ibteam7.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConfigData {
    private String tenantId;
    private String bannerImageUrl;
    private int defaultPropertyId;
    private String defaultPropertyName;
    private DefaultConfig defaultConfig;
    private Map<String, PropertyConfig> propertyConfig;

}
@Data
@NoArgsConstructor
@AllArgsConstructor
class DefaultConfig {
    private GuestsConfig guests;
    private RoomsConfig rooms;
    private WheelchairConfig wheelchair;

}
@Data
@NoArgsConstructor
@AllArgsConstructor
class GuestsConfig {
    private boolean show;
    private int maxNumberOfGuests;
    private List<String> guestsType;

}
@Data
@NoArgsConstructor
@AllArgsConstructor
class RoomsConfig {
    private boolean show;
    private int maxNumberOfRooms;

}
@Data
@NoArgsConstructor
@AllArgsConstructor
class WheelchairConfig {
    private boolean show;

}
@Data
@NoArgsConstructor
@AllArgsConstructor
class PropertyConfig {
    private GuestsConfig guests;
    private RoomsConfig rooms;
    private WheelchairConfig wheelchair;

}

