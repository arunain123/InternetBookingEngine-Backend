package com.example.ibteam7.repository;



import com.example.ibteam7.entity.RoomReservationDates;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RoomReservationDatesRepository extends JpaRepository<RoomReservationDates, UUID> {
}