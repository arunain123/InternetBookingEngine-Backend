package com.example.ibteam7.service;

import com.example.ibteam7.dto.BookingRequestDto;
import com.example.ibteam7.entity.UniqueBookingEntity;
import com.example.ibteam7.repository.UniqueBookingRepository;
import com.example.ibteam7.utility.GraphqlQuery;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class PerformBookingService {



    @Autowired
    private GraphqlQuery graphqlQuery;


    @Autowired
    private UpdateBookingDataService updateBookingDataService;

    @Autowired
    private UniqueBookingRepository uniqueBookingRepository;

    @Autowired
    private ValidateBookingTransactionService validateBookingTransactionService;

    public String performBooking(List<Integer> validAvailabilityIds, BookingRequestDto bookingRequestDto) {


        String startDate = bookingRequestDto.getCheckInDate();
        String endDate = bookingRequestDto.getCheckOutDate();
        String adultCount = bookingRequestDto.getGuestInfo().get("adults").asText();
        String childCount = bookingRequestDto.getGuestInfo().get("children").asText();
        String totalCost = Double.toString(bookingRequestDto.getTotalPrice());
        String amountDueAtResort = Double.toString(bookingRequestDto.getAmountDueAtResort());

        String bookingRequestBody = buildGraphQLBookingMutation(startDate, endDate, adultCount, childCount, totalCost, amountDueAtResort);
        ResponseEntity<JsonNode> bookingResponse = graphqlQuery.executeGraphQLQueryHelper(bookingRequestBody);
        int bookingId= bookingResponse.getBody().get("data").get("createBooking").get("booking_id").asInt();



       updateRoomAvailability(validAvailabilityIds, bookingId);
       updateBookingDataService.updateBookingData(bookingId, bookingRequestDto);

       UniqueBookingEntity uniqueBookingEntity= uniqueBookingRepository.findById(bookingId).orElse(null);

       return uniqueBookingEntity.getBookingIdMapper().toString();

    }

    private String buildGraphQLBookingMutation(String startDate, String endDate, String adultCount, String childCount, String totalCost, String amountDueAtResort) {



        double doubleAmount = Double.parseDouble(amountDueAtResort);
        int integerAmount = (int) doubleAmount;
        String amountDueatResort = Integer.toString(integerAmount);

        double doubleCost = Double.parseDouble(totalCost);
        int integerCost = (int) doubleCost;
        String totalCoststring = Integer.toString(integerCost);


        return String.format("mutation { createBooking( data: {check_in_date: \\\"%s\\\", check_out_date: \\\"%s\\\", adult_count: " + adultCount  +", child_count: " + childCount + ", total_cost: " + totalCoststring + ", amount_due_at_resort: " + amountDueatResort + ", guest: {connect: {guest_id: 1}}, promotion_applied: {connect: {promotion_id: 5}}, property_booked: {connect: {property_id: 7}}, booking_status: {connect: {status_id: 1}}} ) { booking_id } }", startDate, endDate);
}

    private String buildGraphQRoomAvailabilityMutation(int availabilityId, int bookingId) {

        return String.format("mutation { updateRoomAvailability( where: {availability_id:" + availabilityId + "} data: {booking: {connect: {booking_id: " + bookingId + " }}} ) { availability_id booking_id date } }");
    }

    public void updateRoomAvailability(List<Integer> validAvailabilityIds, int bookingId) {
        for (Integer availabilityId : validAvailabilityIds) {
            String availabilityRequestBody = buildGraphQRoomAvailabilityMutation(availabilityId, bookingId);
            ResponseEntity<JsonNode> availabilityResponse = graphqlQuery.executeGraphQLQueryHelper(availabilityRequestBody);

        }
    }

}
