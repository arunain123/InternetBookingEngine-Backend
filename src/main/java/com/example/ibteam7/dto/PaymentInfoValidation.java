package com.example.ibteam7.dto;


import lombok.*;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Component
public class PaymentInfoValidation {
    String cardNo;
    String expiryMonth;
    String expiryYear;
}
