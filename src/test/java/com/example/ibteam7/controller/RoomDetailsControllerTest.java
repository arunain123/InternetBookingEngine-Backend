package com.example.ibteam7.controller;

import com.example.ibteam7.dto.RoomDetailsRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RoomDetailsControllerTest {

    private MockMvc mockMvc;



    @InjectMocks
    private RoomDetailsController roomDetailsController;



    @Test
    public void testPostDates() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(roomDetailsController).build();

        // Create a sample RoomDetailsRequestDto

        RoomDetailsRequestDto requestDto = new RoomDetailsRequestDto();
        requestDto.setProperty("Example Property");
        requestDto.setStartDate("2024-04-01");
        requestDto.setEndDate("2024-04-10");
        requestDto.setRoomCount(2);
        requestDto.setRoomType(new ArrayList());
        requestDto.setBedType(new ArrayList());
        requestDto.setPriceLessThan(1000);
        requestDto.setSort(0);

        mockMvc.perform(post("/api/v1/dates")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(requestDto))) // Convert object to JSON string
                .andExpect(status().isOk());

    }

    // Utility method to convert object to JSON string
    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}