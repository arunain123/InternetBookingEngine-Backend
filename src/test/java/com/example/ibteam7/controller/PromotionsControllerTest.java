package com.example.ibteam7.controller;

import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class PromotionsControllerTest {


    public static void testGetPromoCodes(MockMvc mockMvc) throws Exception{
        mockMvc.perform(get("/api/v1/promocodes"))
                .andExpect(status().isOk());
//                .andExpect(content().contentType("application/json"))
//                .andExpect(jsonPath("$.data.promotions").isArray())
//                .andExpect(jsonPath("$.data.promotions[0].promotion_id").value(1));


    }

    public static void testValidatePromoCode(MockMvc mockMvc) throws Exception{
        mockMvc.perform(get("/api/v1/validatepromo?promoCode=TEST&roomCount=1&startDate=2024-04-07&endDate=2024-04-12"))
                .andExpect(status().isOk());
    }




}