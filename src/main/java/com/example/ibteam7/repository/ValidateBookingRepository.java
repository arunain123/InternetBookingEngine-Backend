package com.example.ibteam7.repository;

import com.example.ibteam7.entity.UniqueBookingEntity;
import com.example.ibteam7.entity.ValidateBookingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ValidateBookingRepository extends JpaRepository<ValidateBookingEntity, Integer> {
}
