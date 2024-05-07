package com.example.ibteam7.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomTypePriceDto {

    String roomTypeName;
    int basicNightlyRate;
    String date;
}
