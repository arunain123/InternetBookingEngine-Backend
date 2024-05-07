package com.example.ibteam7.controller;

import com.example.ibteam7.dto.ConfigDto;
import com.example.ibteam7.service.ConfigService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ConfigLoadControllerTest {
    @Mock
    private ConfigService configService;

    @InjectMocks
    private ConfigLoadController configController;

    public static void getConfig(MockMvc mockMvc) throws Exception {
        mockMvc.perform(get("/config"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$[0].tenantId").value("1"));

    }
}