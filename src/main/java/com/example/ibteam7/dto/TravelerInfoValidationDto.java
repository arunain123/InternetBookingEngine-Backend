package com.example.ibteam7.dto;

import lombok.*;
import org.springframework.stereotype.Component;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TravelerInfoValidationDto {
    String firstName;
    String lastName;
    String phoneNo;
    String emailId;
}
