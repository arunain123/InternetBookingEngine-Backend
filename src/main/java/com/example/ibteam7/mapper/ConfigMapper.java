package com.example.ibteam7.mapper;

import com.example.ibteam7.dto.ConfigDto;
import com.example.ibteam7.entity.ConfigEntity;
import org.springframework.stereotype.Component;

/**
 * Mapper class to convert ConfigDto objects to ConfigEntity objects.
 */
@Component
public class ConfigMapper {

    /**
     * Maps a ConfigDto object to a ConfigEntity object.
     * @param configDto The ConfigDto object to map.
     * @return ConfigEntity object mapped from the ConfigDto.
     */
    public ConfigEntity configMapper(ConfigDto configDto){
        ConfigEntity configEntity = new ConfigEntity();
        configEntity.setTenantId(configDto.getTenantId());
        configEntity.setBannerImageUrl(configDto.getBannerImageUrl());
        configEntity.setDefaultPropertyId(configDto.getDefaultPropertyId());
        configEntity.setDefaultPropertyName(configDto.getDefaultPropertyName());
        configEntity.setPropertyConfig(configDto.getPropertyConfig());
        return configEntity;
    }
}
