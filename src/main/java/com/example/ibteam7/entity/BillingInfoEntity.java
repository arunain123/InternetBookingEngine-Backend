package com.example.ibteam7.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Entity
public class BillingInfoEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer billingId;
    private String firstName;
    private String lastName;
    private String mailingAddress1;
    private String mailingAddress2;
    private String country;
    private String state;
    private String city;
    private String zip;
    private String phoneNo;

    private String emailId;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "booking_id")
    private UniqueBookingEntity uniqueBookingEntity;
}
