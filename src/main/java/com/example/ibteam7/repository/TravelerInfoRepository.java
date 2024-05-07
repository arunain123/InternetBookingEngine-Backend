package com.example.ibteam7.repository;

import com.example.ibteam7.entity.TravelerInfoEntity;
import com.example.ibteam7.entity.UniqueBookingEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TravelerInfoRepository extends JpaRepository<TravelerInfoEntity, Integer> {

    @Query("SELECT ti FROM TravelerInfoEntity ti WHERE ti.uniqueBookingEntity.bookingId = :bookingId")
    TravelerInfoEntity findByBookingId(Integer bookingId);

    @Transactional
    @Modifying
    @Query("DELETE FROM TravelerInfoEntity r WHERE r.uniqueBookingEntity.bookingId = :bookingId")
    void deleteByBookingId(@Param("bookingId") Integer bookingId);
}

