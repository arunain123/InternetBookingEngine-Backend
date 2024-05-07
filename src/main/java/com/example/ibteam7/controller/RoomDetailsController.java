package com.example.ibteam7.controller;

import com.example.ibteam7.config.Constants;
import com.example.ibteam7.dto.*;
import com.example.ibteam7.mapper.RoomDetailsMapper;
//import com.example.ibteam7.mapper.RoomDetailsRequestMapper;
import com.example.ibteam7.service.PromotionService;
import com.example.ibteam7.service.PropertyService;
import com.example.ibteam7.service.RoomTypeDetailsService;
import com.example.ibteam7.service.RoomTypePriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Controller class for handling room details-related operations.
 */
@RestController
@CrossOrigin(origins = Constants.FRONTEND_URL)
@RequestMapping(Constants.REQUEST_MAPPING)
public class RoomDetailsController {

    @Autowired
    private RoomTypeDetailsService roomTypeDetailsService;


    private List<RoomTypeDetailsDto> allRoomTypeDetails = new ArrayList<>();

    /**
     * Get room details response DTO.
     *
     * @return List of RoomDetailsResponseDto containing room details.
     */
    @GetMapping("/roomcartdetails")
    public List<RoomDetailsResponseDto> getRoomDetailsResponseDto(@RequestParam String property, @RequestParam String startDate, @RequestParam String endDate, @RequestParam int roomCount, @RequestParam(required = false) String bedType, @RequestParam(required = false) String roomType, @RequestParam int priceLessThan, @RequestParam int sort) {
        allRoomTypeDetails.clear();

        List<String> roomTypeArr = Arrays.asList(roomType.split(","));
        List<String> bedTypeArr = Arrays.asList(bedType.split(","));

        return roomTypeDetailsService.findAllRoomDetails(allRoomTypeDetails, startDate, endDate, property, roomTypeArr, bedTypeArr, sort, roomCount, priceLessThan);
    }

    }



