package com.example.ibteam7.repository;

import com.example.ibteam7.entity.BillingInfoEntity;
import com.example.ibteam7.entity.ConfigEntity;
import com.example.ibteam7.entity.UniqueBookingEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BillingInfoRepository extends JpaRepository<BillingInfoEntity, Integer> {

    @Query("SELECT ti FROM BillingInfoEntity ti WHERE ti.uniqueBookingEntity.bookingId = :bookingId")
    BillingInfoEntity findByBookingId(Integer bookingId);


    @Transactional
    @Modifying
    @Query("DELETE FROM BillingInfoEntity r WHERE r.uniqueBookingEntity.bookingId = :bookingId")
    void deleteByBookingId(@Param("bookingId") Integer bookingId);
}
