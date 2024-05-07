package com.example.ibteam7.controller;

import com.example.ibteam7.config.Constants;
import com.example.ibteam7.dto.*;
import com.example.ibteam7.entity.UserBookingsEntity;
import com.example.ibteam7.service.*;
import com.example.ibteam7.utility.GraphqlQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller class for handling room details-related operations.
 */
@RestController
@CrossOrigin(origins = Constants.FRONTEND_URL)
@RequestMapping(Constants.REQUEST_MAPPING)
public class BookingController {



    @Autowired
    private ValidateBookingService validateBookingService;

    @Autowired
    private PerformBookingService performBookingService;


    @Autowired
    private CancelBookingService cancelBookingService;

    @Autowired
    private GraphqlQuery graphqlQuery;


    @Autowired
    private ValidateBookingTransactionService validateBookingTransactionService;

    @Autowired
    private UserBookingsService userBookingsService;


    /**
     * Get room details response DTO.
     *
     * @return List of RoomDetailsResponseDto containing room details.
     */
    @PostMapping("/createbooking")
    public String getBookingDetails(@RequestBody BookingRequestDto bookingRequestDto) {
        List<Integer> validAvailabilityIds = validateBookingService.validateBooking(bookingRequestDto.getRoomTypeName(), bookingRequestDto.getCheckInDate(), bookingRequestDto.getCheckOutDate(), bookingRequestDto.getNumberOfRooms(), bookingRequestDto.getPropertyId());
        if(!validAvailabilityIds.isEmpty()){
            int differenceDate=(int)validateBookingService.calculateDateDifference(bookingRequestDto.getCheckInDate(),bookingRequestDto.getCheckOutDate());
            List<Integer> bookedAvailabilityIds= validateBookingTransactionService.handleConcurrentBooking(validAvailabilityIds, bookingRequestDto.getNumberOfRooms()*differenceDate);
            if(bookedAvailabilityIds.isEmpty()) return "Booking failed";
           return performBookingService.performBooking(bookedAvailabilityIds, bookingRequestDto);
        }
        return "Booking failed";
    }

    @GetMapping("/cancelbooking")
    public String cancelBooking(@RequestParam (required = true) String bookingId) {
        return cancelBookingService.cancelBooking(Integer.parseInt(bookingId));
    }

    @GetMapping("/mybookings")
    public List<UserBookingsDto> mybookings(@RequestParam(required = true) String emailId){
        return userBookingsService.getUserBookings(emailId);
    }



}
