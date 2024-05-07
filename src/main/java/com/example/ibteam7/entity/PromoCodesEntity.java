package com.example.ibteam7.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Entity
public class PromoCodesEntity {


    private double priceFactor;
    @Id
    private String promoCodeTitle;
    private String promoCodeDescription;
    private int minimumNoOfDays;
    private String promoCodeStartDate;
    private String promoCodeEndDate;
}
