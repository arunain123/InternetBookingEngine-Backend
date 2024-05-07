package com.example.ibteam7.controller;

import com.example.ibteam7.config.Constants;
import com.example.ibteam7.dto.RoomDto;
import com.example.ibteam7.service.PropertyService;
import com.example.ibteam7.service.RoomService;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * HealthController is a simple controller that is used to check if the server
 * is running
 * and to check if the server is healthy
 *
 */
@RestController
@CrossOrigin(origins = Constants.FRONTEND_URL)
@RequestMapping(Constants.REQUEST_MAPPING)
public class HealthController {

    /**
     * @return a welcome message
     */
    @GetMapping("/")
    public String home() {
        return "Welcome to the IB Team 7 server";
    }

    /**
     * @return a health check message
     *         This is used to check if the server is running
     */
    @GetMapping("/test")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("I am a healthy running server");
    }






}
