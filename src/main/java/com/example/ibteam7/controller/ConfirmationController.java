package com.example.ibteam7.controller;


import com.example.ibteam7.config.Constants;
import com.example.ibteam7.dto.ConfirmationResponseDto;
import com.example.ibteam7.dto.RoomSummaryRequestDto;
import com.example.ibteam7.service.ConfirmationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = Constants.FRONTEND_URL)
@RequestMapping(Constants.REQUEST_MAPPING)
public class ConfirmationController {

    @Autowired
    private ConfirmationService confirmationService;

    @GetMapping("/confirmation/{bookingId}")
    public ConfirmationResponseDto getConfirmationDetails(@PathVariable int bookingId) {

        return confirmationService.getConfirmationDetails(bookingId);
    }

}
