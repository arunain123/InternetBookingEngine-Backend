package com.example.ibteam7.dto;

import lombok.*;
import org.springframework.stereotype.Component;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PaymentInfoValidationDto {

    String cardNo;
    String expiryMonth;
    String expiryYear;
}
