package com.example.ibteam7.dto;


import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserBookingsDto {

    int bookingMapperId;
    String firstName;
    String lastName;
    String travellerEmailId;
    String startDate;
    String endDate;
}
