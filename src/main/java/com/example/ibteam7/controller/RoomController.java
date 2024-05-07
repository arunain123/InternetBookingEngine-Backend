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
 * Controller class for handling room-related operations.
 */
@RestController
@CrossOrigin(origins = Constants.FRONTEND_URL)
@RequestMapping(Constants.REQUEST_MAPPING)
public class RoomController {

    @Autowired
    private RoomService roomService;

    @Autowired
    private PropertyService propertyService;

    /**
     * Get all properties.
     * @return JsonNode containing details of all properties in the GraphQL database.
     */
    @GetMapping("/property")
    public JsonNode getProperty() {
        ResponseEntity<JsonNode> response = propertyService.findAllHotels();
        return response.getBody();
    }

    /**
     * Get all rooms.
     * @return List of RoomDto containing details of all rooms.
     */
    @GetMapping("/rooms")
    public List<RoomDto> getRooms(){
        return roomService.findAllRooms();
    }
}
