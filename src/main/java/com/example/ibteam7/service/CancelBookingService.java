package com.example.ibteam7.service;

import com.example.ibteam7.entity.UniqueBookingEntity;
import com.example.ibteam7.repository.*;
import com.example.ibteam7.utility.GraphqlQuery;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
public class CancelBookingService {
    @Autowired
    private UniqueBookingRepository uniqueBookingRepository;

    @Autowired
    private TravelerInfoRepository travelerInfoRepository;

    @Autowired
    private BillingInfoRepository billingInfoRepository;

    @Autowired
    private PaymentInfoRepository paymentInfoRepository;

    @Autowired
    private RoomSummaryRepository roomSummaryRepository;

    @Autowired
    private GraphqlQuery graphqlQuery;

    public String cancelBooking(int bookingMapperId) {
        int bookingId = uniqueBookingRepository.findByBookingIdMapper(bookingMapperId).getBookingId();
        travelerInfoRepository.deleteByBookingId(bookingId);
        billingInfoRepository.deleteByBookingId(bookingId);
        paymentInfoRepository.deleteByBookingId(bookingId);
        roomSummaryRepository.deleteByBookingId(bookingId);

        uniqueBookingRepository.deleteById(bookingId);

        String bookingRequestBody = buildGraphQLBookingMutation(bookingId);
        ResponseEntity<JsonNode> bookingResponse = graphqlQuery.executeGraphQLQueryHelper(bookingRequestBody);


        return "Cancelled";
    }

    private String buildGraphQLBookingMutation(Integer bookingId) {

        return String.format("mutation MyMutation { updateBooking( where: {booking_id: " + bookingId + "} data: {booking_status: {connect: {status_id: 2}}} ) { status_id booking_id booking_status { status } } }");
    }
}
