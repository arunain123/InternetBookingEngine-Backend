package com.example.ibteam7.dto;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class BookingRequestDto {
    @Email
    private String userEmail;
    @NotNull
    private String checkInDate;
    @NotNull
    private String checkOutDate;
    @NotNull
    private int propertyId;
//    @NotNull
//    private int roomTypeId;

    private String promotionTitle;
    @PositiveOrZero
    private double amountDueAtResort;
    @Positive
    private double totalPrice;
    @Positive
    private int numberOfRooms;
    @NotNull
    private String roomTypeName;
//    @Positive
//    private double totalAmountPerNight;

    private String promotionDescription;

    private JsonNode travellerInfo;

    private JsonNode billingInfo;

    private JsonNode paymentInfo;

    private JsonNode guestInfo;

//    private JsonNode priceInfo;
//
//    private JsonNode promoInfo;

}