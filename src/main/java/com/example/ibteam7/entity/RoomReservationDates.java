package com.example.ibteam7.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "VALIDATE_BOOKING")
public class RoomReservationDates {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private int roomId;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
}