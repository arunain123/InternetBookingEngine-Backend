package com.example.ibteam7.service;

import com.example.ibteam7.dto.PromotionsDto;
import com.example.ibteam7.dto.RoomDetails;
import com.example.ibteam7.utility.GraphqlQuery;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

/**
 * Service class for managing promotions.
 */
@Service
public class PromotionService {

    @Autowired
    public GraphqlQuery graphqlQuery;


    /**
     * Retrieves all promotions based on the room details.
     * @return List of promotions.
     */
    public List<PromotionsDto> findAllPromotions(String startDate, String endDate) {

        String QUERY = "{ listPromotions(where: {is_deactivated: {equals: false}}) { minimum_days_of_stay price_factor promotion_description promotion_id promotion_title } }";

        ResponseEntity<JsonNode> allRoomsResponse = graphqlQuery.executeGraphQLQueryHelper(QUERY);
        JsonNode body = allRoomsResponse.getBody();

        JsonNode listPromotions = body.get("data").get("listPromotions");
        List<PromotionsDto> promotionsDtoList = new ArrayList<>();
        long lengthOfStay = findRange(startDate, endDate);
        boolean containsSaturdayAndSunday = isContainsSaturdayAndSunday(startDate, endDate);

        for (JsonNode promotion : listPromotions) {
            int minimumDaysOfStay = promotion.get("minimum_days_of_stay").asInt();
            Double priceFactor = promotion.get("price_factor").asDouble();
            String promotionDescription = promotion.get("promotion_description").asText();
            int promotionId = promotion.get("promotion_id").asInt();
            String promotionTitle = promotion.get("promotion_title").asText();

            PromotionsDto promotionsDto = new PromotionsDto();
            promotionsDto.setPromotionId(promotionId);
            promotionsDto.setPromotionDescription(promotionDescription);
            promotionsDto.setPriceFactor(priceFactor);
            promotionsDto.setPromotionTitle(promotionTitle);
            promotionsDto.setMinimumNoOfDays(minimumDaysOfStay);

            if (minimumDaysOfStay > lengthOfStay) continue;
            if (promotionTitle.toLowerCase().contains("weekend") && !containsSaturdayAndSunday) continue;

            promotionsDtoList.add(promotionsDto);
        }

        return promotionsDtoList;
    }

    /**
     * Calculates the range between two dates.
     * @param startDateStr The start date string.
     * @param endDateStr The end date string.
     * @return The range between the two dates.
     */
    long findRange(String startDateStr, String endDateStr) {
        LocalDate startDate = LocalDate.parse(startDateStr.substring(0, 10)); // Extracting only the date part
        LocalDate endDate = LocalDate.parse(endDateStr.substring(0, 10)); // Extracting only the date part
        return ChronoUnit.DAYS.between(startDate, endDate) + 1; // Including the end date
    }

    /**
     * Checks if the date range contains both Saturday and Sunday.
     * @param startDateStr The start date string.
     * @param endDateStr The end date string.
     * @return True if the range contains both Saturday and Sunday, false otherwise.
     */
    boolean isContainsSaturdayAndSunday(String startDateStr, String endDateStr) {
        LocalDate startDate = LocalDate.parse(startDateStr.substring(0, 10)); // Extracting only the date part
        LocalDate endDate = LocalDate.parse(endDateStr.substring(0, 10)); // Extracting only the date part
        boolean containsSaturday = false;
        boolean containsSunday = false;
        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
            if (date.getDayOfWeek().equals(java.time.DayOfWeek.SATURDAY)) containsSaturday = true;
            else if (date.getDayOfWeek().equals(java.time.DayOfWeek.SUNDAY)) containsSunday = true;
            if (containsSaturday && containsSunday) break;
        }
        return (containsSaturday && containsSunday);
    }
}
