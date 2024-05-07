package com.example.ibteam7.service;

import com.example.ibteam7.dto.ConfirmationResponseDto;
//import com.example.ibteam7.dto.RoomSummary;
import com.example.ibteam7.dto.RoomSummaryRequestDto;
import com.example.ibteam7.entity.*;
import com.example.ibteam7.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConfirmationService {

    @Autowired
    private UniqueBookingRepository uniqueBookingRepository;

    @Autowired
    private BillingInfoRepository billingInfoRepository;

    @Autowired
    private PaymentInfoRepository paymentInfoRepository;

    @Autowired
    private TravelerInfoRepository travelerInfoRepository;

    @Autowired
    private RoomSummaryRepository roomSummaryRepository;


    public ConfirmationResponseDto getConfirmationDetails(int bookingMapperId) {

        UniqueBookingEntity uniqueBookingEntity = uniqueBookingRepository.findByBookingIdMapper(bookingMapperId);

        if(uniqueBookingEntity == null) {
            return null;
        }

        TravelerInfoEntity travelerInfoEntity = travelerInfoRepository.findByBookingId(uniqueBookingEntity.getBookingId());

        BillingInfoEntity billingInfoEntity = billingInfoRepository.findByBookingId(uniqueBookingEntity.getBookingId());
        PaymentInfoEntity paymentInfoEntity = paymentInfoRepository.findByBookingId(uniqueBookingEntity.getBookingId());
        RoomSummaryEntity roomSummaryEntity = roomSummaryRepository.findByBookingId(uniqueBookingEntity.getBookingId());

        return new ConfirmationResponseDto(travelerInfoEntity, billingInfoEntity, paymentInfoEntity, roomSummaryEntity);





    }



}
