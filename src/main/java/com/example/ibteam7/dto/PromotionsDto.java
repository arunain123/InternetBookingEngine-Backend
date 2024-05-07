package com.example.ibteam7.dto;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class PromotionsDto {
    int promotionId;
    double priceFactor;
    String promotionTitle;
    String promotionDescription;
    int minimumNoOfDays;

}
