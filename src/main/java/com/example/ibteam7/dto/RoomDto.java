package com.example.ibteam7.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomDto {
    String date;
    int basicNightlyRate;
    int roomRateId;
}
