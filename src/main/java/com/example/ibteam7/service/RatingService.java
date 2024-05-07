package com.example.ibteam7.service;


import com.example.ibteam7.entity.RatingEntity;
import com.example.ibteam7.repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RatingService {

    @Autowired
    private RatingRepository ratingRepository;



    public void updateRating(String ratingValue, String roomTypeId, String propertyId) {

//        RatingEntity ratingEntity=new RatingEntity();
//        ratingEntity.setRating(0.0);
//        ratingEntity.setTotalUsers(0);
//        ratingEntity.setRoomTypeId(roomTypeId);
//        ratingEntity.setPropertyId(Integer.parseInt(propertyId));
//        ratingRepository.save(ratingEntity);
        ratingRepository.updateRating(Integer.parseInt(ratingValue), roomTypeId, Integer.parseInt(propertyId));
    }


    public Map<String,Double> findallRatings(){
        List<RatingEntity> ratingEntities = ratingRepository.findAll();
        Map<String,Double> ratingMap = new HashMap<>();
        for(RatingEntity ratingEntity:ratingEntities){
            ratingMap.put(ratingEntity.getRoomTypeId(),ratingEntity.getRating());
        }
        return ratingMap;
    }
}
