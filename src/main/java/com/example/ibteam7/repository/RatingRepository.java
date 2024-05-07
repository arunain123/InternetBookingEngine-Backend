package com.example.ibteam7.repository;


import com.example.ibteam7.entity.PromoCodesEntity;
import com.example.ibteam7.entity.RatingEntity;
import com.example.ibteam7.entity.RoomSummaryEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RatingRepository extends JpaRepository<RatingEntity, Integer> {

    @Transactional
    @Modifying
    @Query("update RatingEntity r set r.rating = ((r.rating * r.totalUsers) + :ratingValue) / (r.totalUsers + 1), r.totalUsers = r.totalUsers + 1 where r.roomTypeId = :roomTypeId and r.propertyId = :propertyId")
//    @Query("create RatingEntity r set r.rating = 0, r.totalUsers = 0 where r.roomTypeId = :roomTypeId and r.propertyId = :propertyId")

    void updateRating(Integer ratingValue, String roomTypeId, Integer propertyId);

}
