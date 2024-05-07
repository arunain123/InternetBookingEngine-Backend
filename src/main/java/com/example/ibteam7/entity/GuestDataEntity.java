package com.example.ibteam7.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Entity
public class GuestDataEntity {
    String userName;
    @Id
    String emailId;
    String password;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "booking_id")
    private UniqueBookingEntity uniqueBookingEntity;


}
