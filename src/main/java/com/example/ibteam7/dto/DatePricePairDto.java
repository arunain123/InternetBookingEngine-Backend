package com.example.ibteam7.dto;

import java.util.Date;

// Custom Pair class to represent a pair of Date and Integer
public class DatePricePairDto {
    private String date;
    private Integer price;

    public DatePricePairDto(String date, Integer price) {
        this.date = date;
        this.price = price;
    }

    public String getDate() {
        return date;
    }

    public Integer getPrice() {
        return price;
    }
}