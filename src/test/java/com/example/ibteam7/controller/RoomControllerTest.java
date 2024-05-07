package com.example.ibteam7.controller;

import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class RoomControllerTest {

    public static void testGetPropertyList(MockMvc mockMvc) throws Exception {
        mockMvc.perform(get("/api/v1/property"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.data.listProperties").isArray())
                .andExpect(jsonPath("$.data.listProperties[0].property_id").value(1))
                .andExpect(jsonPath("$.data.listProperties[0].property_name").value("Team 1 Hotel"));


    }

    public static void testGetMinimumNightlyRates(MockMvc mockMvc) throws Exception {
        mockMvc.perform(get("/api/v1/rooms"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].roomRateId").value(1))
                .andExpect(jsonPath("$[0].basicNightlyRate").isNotEmpty())
                .andExpect(jsonPath("$[0].date").isNotEmpty());


    }


    public static void testGetRoomDetails(MockMvc mockMvc) {
    }
}