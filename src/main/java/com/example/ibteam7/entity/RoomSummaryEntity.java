package com.example.ibteam7.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@Data
@NoArgsConstructor
public class RoomSummaryEntity {
    Double totalPrice;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer summaryId;


    String promotionTitle;
    String promotionDescription;
    String startDate;
    String endDate;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "booking_id")
    private UniqueBookingEntity uniqueBookingEntity;


}
