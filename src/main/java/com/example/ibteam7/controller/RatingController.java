package com.example.ibteam7.controller;


import com.example.ibteam7.config.Constants;
import com.example.ibteam7.dto.RatingRequestDto;
import com.example.ibteam7.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = Constants.FRONTEND_URL)
@RequestMapping(Constants.REQUEST_MAPPING)
public class RatingController {

    @Autowired
    private RatingService ratingService;


    @PostMapping("/updaterating")
    public void updateRating(@RequestBody RatingRequestDto ratingRequestDto){

        ratingService.updateRating(ratingRequestDto.getRating(), ratingRequestDto.getRoomTypeId(), ratingRequestDto.getPropertyId());
    }

}
