package com.example.ibteam7.controller;

import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class NonVersionControllerTest {

    public static void testBaseURL(MockMvc mockMvc) throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk());

    }
}