package com.example.ibteam7.controller;

import com.example.ibteam7.config.Constants;
import com.example.ibteam7.dto.ConfigDto;
import com.example.ibteam7.entity.ConfigEntity;
import com.example.ibteam7.service.ConfigService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@CrossOrigin(origins = Constants.FRONTEND_URL)
public class ConfigLoadController {

    @Autowired
    private ConfigService configService;

    @Value("classpath:files/configuration.json")
    private Resource configFile;

    @GetMapping("/config")
    public List<ConfigEntity> getConfigData() {
        return configService.getAllConfigDetails();
    }


    @PostMapping("/config")
    public String saveConfigData(@RequestBody ConfigDto configDto){
        configService.saveConfig(configDto);
        return "Config Setup Successful!";
    }




}
