package com.example.ibteam7.repository;


import com.example.ibteam7.entity.UserBookingsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserBookingsRepository extends JpaRepository<UserBookingsEntity, Integer>  {
    @Query("SELECT ub.uniqueBookingEntity.bookingId FROM UserBookingsEntity ub WHERE ub.emailId = :emailId")
    List<Integer> findBookingIdsByEmailId(@Param("emailId") String emailId);
}
