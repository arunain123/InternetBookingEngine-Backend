package com.example.ibteam7.service;

import com.example.ibteam7.dto.ConfigDto;
import com.example.ibteam7.entity.ConfigEntity;
import com.example.ibteam7.mapper.ConfigMapper;
import com.example.ibteam7.repository.ConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConfigService {
    @Autowired
    private ConfigRepository configRepository;

    @Autowired
    private ConfigMapper configMapper;

    public void saveConfig(ConfigDto configdto) {
        ConfigEntity configEntity = configMapper.configMapper(configdto);
        configRepository.save(configEntity);
    }


    public List<ConfigEntity> getAllConfigDetails() {
        return configRepository.findAll();
    }
}
