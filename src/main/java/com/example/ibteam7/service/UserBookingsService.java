package com.example.ibteam7.service;

import com.example.ibteam7.dto.BookingRequestDto;
import com.example.ibteam7.dto.ConfirmationResponseDto;
import com.example.ibteam7.dto.UserBookingsDto;
import com.example.ibteam7.entity.*;
import com.example.ibteam7.repository.RoomSummaryRepository;
import com.example.ibteam7.repository.TravelerInfoRepository;
import com.example.ibteam7.repository.UniqueBookingRepository;
import com.example.ibteam7.repository.UserBookingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.secretsmanager.endpoints.internal.Value;

import java.util.ArrayList;
import java.util.List;


@Service
public class UserBookingsService {


    @Autowired
    private UserBookingsRepository userBookingsRepository;

    @Autowired
    private UniqueBookingRepository uniqueBookingRepository;

    @Autowired
    private TravelerInfoRepository travelerInfoRepository;

    @Autowired
    private RoomSummaryRepository roomSummaryRepository;


    public List<UserBookingsDto> getUserBookings(String emailId) {
        List<Integer> bookingIdList = userBookingsRepository.findBookingIdsByEmailId(emailId);
        System.out.println("BOOKING ID LIST.......................");
        System.out.println(bookingIdList);
        List<UserBookingsDto> userBookingsDtoList = new ArrayList<>();
        for (Integer bookingId : bookingIdList) {

            UniqueBookingEntity uniqueBookingEntity = uniqueBookingRepository.findById(bookingId).orElse(null);
            if (uniqueBookingEntity == null) continue;
            UserBookingsDto userBookingsDto = new UserBookingsDto();
            System.out.println("BOOKING MAPPER ID");
            System.out.println(uniqueBookingEntity.getBookingIdMapper());
            userBookingsDto.setBookingMapperId(uniqueBookingEntity.getBookingIdMapper());

            TravelerInfoEntity travelerInfoEntity = travelerInfoRepository.findByBookingId(bookingId);
            if (travelerInfoEntity == null) continue;


            userBookingsDto.setFirstName(travelerInfoEntity.getFirstName());
            userBookingsDto.setLastName(travelerInfoEntity.getLastName());
            userBookingsDto.setTravellerEmailId(travelerInfoEntity.getEmailId());


            RoomSummaryEntity roomSummaryEntity = roomSummaryRepository.findByBookingId(bookingId);
            if (roomSummaryEntity == null) continue;

            userBookingsDto.setStartDate(roomSummaryEntity.getStartDate());
            userBookingsDto.setEndDate(roomSummaryEntity.getEndDate());

            userBookingsDtoList.add(userBookingsDto);

        }

        System.out.println("user list ...................");
        System.out.println(userBookingsDtoList);
        return userBookingsDtoList;
    }

}
