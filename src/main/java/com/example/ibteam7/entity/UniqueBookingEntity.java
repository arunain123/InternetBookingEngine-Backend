package com.example.ibteam7.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UuidGenerator;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Entity
public class UniqueBookingEntity {

    private Integer bookingIdMapper;

    @Id
    private Integer bookingId;
}
