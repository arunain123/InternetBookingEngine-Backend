package com.example.ibteam7.controller;

import com.example.ibteam7.dto.TravelerInfoValidationDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static com.example.ibteam7.controller.RoomDetailsControllerTest.asJsonString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class FormFieldsValidationControllerTest {


    public static void testTravelerInfoValidation(MockMvc mockMvc) throws Exception {
        TravelerInfoValidationDto travelerInfoValidationDto = new TravelerInfoValidationDto();
        travelerInfoValidationDto.setFirstName("John");
        travelerInfoValidationDto.setLastName("Doe");
        travelerInfoValidationDto.setEmailId("johndoe@gmail.com");
        travelerInfoValidationDto.setPhoneNo("9876543210");

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonContent = objectMapper.writeValueAsString(travelerInfoValidationDto);

        mockMvc.perform(post("/api/v1/validatetravelerinfo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isOk());



    }

}
