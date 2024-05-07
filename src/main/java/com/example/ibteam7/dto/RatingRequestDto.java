package com.example.ibteam7.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
@AllArgsConstructor
public class RatingRequestDto {
    private String rating;
    private String roomTypeId;
    private String propertyId;

}
