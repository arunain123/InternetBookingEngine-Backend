package com.example.ibteam7.dto;

import lombok.*;
import org.springframework.stereotype.Component;

@Component@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BillingInfoValidation {
    private String firstName;
    private String lastName;
    private String mailingAddress1;
    private String mailingAddress2;
    private String country;
    private String state;
    private String city;
    private String zip;
    private String phoneNo;
    private String emailId;
}
