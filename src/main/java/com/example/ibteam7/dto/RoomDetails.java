package com.example.ibteam7.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@Getter
@Setter
public class RoomDetails {

    private String property;
    private String startDate;
    private String endDate;

    private int roomCount;

    private List<String> roomType;

    private List<String> bedType;

    private int priceLessThan;

    private int sort;


}
