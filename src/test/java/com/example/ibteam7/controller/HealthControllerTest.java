package com.example.ibteam7.controller;

import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class HealthControllerTest {

    public static void testHealth(MockMvc mockMvc) throws Exception {
        mockMvc.perform(get("/api/v1/test"))
                .andExpect(status().isOk());
    }
    public static void testHealth2(MockMvc mockMvc) throws Exception {
        mockMvc.perform(get("/api/v1/"))
                .andExpect(status().isOk());
    }
}