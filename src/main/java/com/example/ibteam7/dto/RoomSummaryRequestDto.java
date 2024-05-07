package com.example.ibteam7.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RoomSummaryRequestDto {
    int totalPrice;
    String promotionTitle;
    String promotionDescription;
    String startDate;
    String endDate;
}
