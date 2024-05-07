package com.example.ibteam7.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TravelerInfoValidation {
    String firstName;
    String lastName;
    String phoneNo;
    String emailId;






}
