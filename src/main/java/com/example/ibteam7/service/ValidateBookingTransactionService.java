package com.example.ibteam7.service;

import com.example.ibteam7.entity.ValidateBookingEntity;
import com.example.ibteam7.repository.ValidateBookingRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ValidateBookingTransactionService {

    @Autowired
    private ValidateBookingRepository validateBookingRepository;

    @Transactional
    public List<ValidateBookingEntity> insertBooking(List<Integer> transactionInformations)
    {

        List<ValidateBookingEntity> validateBookingEntityList=new ArrayList<>();
        for(Integer id: transactionInformations){
            ValidateBookingEntity validateBookingEntity= new ValidateBookingEntity(id);
            validateBookingEntityList.add(validateBookingEntity);
        }
        List<ValidateBookingEntity> bookingTransactionInformationList = validateBookingRepository.saveAll(validateBookingEntityList);
        if(!bookingTransactionInformationList.isEmpty())
        {
            return bookingTransactionInformationList;
        }
        return null;
    }


    public List<Integer> handleConcurrentBooking(List<Integer> validAvailabilityIds, int roomsNeeded) {
        int startIndex = 0;
        List<Integer>  insertedavailabilityIds = new ArrayList<>();

        while (startIndex <= validAvailabilityIds.size() - roomsNeeded) {

            try {
                List<Integer> transactionsToSave = validAvailabilityIds.subList(startIndex, startIndex + roomsNeeded);
                List<ValidateBookingEntity> bookingTransactionInformationList = insertBooking(transactionsToSave);

                for (ValidateBookingEntity transaction : bookingTransactionInformationList) {
                    insertedavailabilityIds.add(transaction.getAvailabilityId());
                }
                return  insertedavailabilityIds;

            } catch (Exception e) {
               log.error("Currently RoomAvailability Ids are already occupied");
            }

            startIndex++;
        }


        return insertedavailabilityIds;
    }


}

