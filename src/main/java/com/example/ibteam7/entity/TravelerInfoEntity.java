package com.example.ibteam7.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Entity
public class TravelerInfoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer travelerId;
    String firstName;
    String lastName;
    String phoneNo;
    String emailId;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "booking_id")
    private UniqueBookingEntity uniqueBookingEntity;
}
