package com.example.ibteam7.dto;

import com.example.ibteam7.entity.BillingInfoEntity;
import com.example.ibteam7.entity.PaymentInfoEntity;
import com.example.ibteam7.entity.RoomSummaryEntity;
import com.example.ibteam7.entity.TravelerInfoEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class ConfirmationResponseDto {


    TravelerInfoEntity travelerInfoEntity;
    BillingInfoEntity billingInfoEntity;
    PaymentInfoEntity paymentInfoEntity;
    RoomSummaryEntity roomSummaryEntity;
}
