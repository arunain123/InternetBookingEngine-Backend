package com.example.ibteam7.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomTypeDetailsDto {
    String date;
    int roomId;
    String roomTypeName;
    int maxCapacity;
    int area;
    int singleBed;
    int doubleBed;
}







