package com.example.ibteam7.dto;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RoomDetailsResponseDto {
    String date;
    int roomId;
    String roomTypeName;
    int maxCapacity;
    int area;
    int singleBed;
    int doubleBed;
    Double avgPrice;
    int count;
    List<PromotionsDto> promotionsDtoList;
    List<DatePricePairDto> datePricePairDtoList;
    Double rating;
}