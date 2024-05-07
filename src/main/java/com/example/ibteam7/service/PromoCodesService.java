package com.example.ibteam7.service;


import com.example.ibteam7.dto.PromotionsDto;
import com.example.ibteam7.entity.PromoCodesEntity;
import com.example.ibteam7.repository.PromoCodesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;


/**
 * Service class for managing promo codes.
 */
@Service
public class PromoCodesService {


    @Autowired
    private PromoCodesRepository promoCodesRepository;

    /**
     * Retrieves all promo codes.
     * @return List of promo codes.
     */

    public List<PromoCodesEntity> getPromoCodes() {
        List<PromoCodesEntity> promoCodesEntityList = new ArrayList<>();

        PromoCodesEntity promoCodesEntity1 = new PromoCodesEntity( 0.8, "HOLI20", "Get 20% discount for holi week", 2, "2024-03-25T00:00:00.000Z", "2024-03-31T00:00:00.000Z");
        PromoCodesEntity promoCodesEntity2 = new PromoCodesEntity( 0.95, "APRILSPECIAL5", "Get 5% discount for whole of april month", 3, "2024-04-01T00:00:00.000Z", "2024-04-30T00:00:00.000Z");
        PromoCodesEntity promoCodesEntity3 = new PromoCodesEntity( 0.9, "WELCOME10", "Offers a 10% discount for first-time users", 2, "2024-04-01T00:00:00.000Z", "2024-05-20T00:00:00.000Z");
        PromoCodesEntity promoCodesEntity4 = new PromoCodesEntity( 0.67, "STAY3PAY2", "Stay for 3 nights and pay for only 2 nights", 3, "2024-04-05T00:00:00.000Z", "2024-04-20T00:00:00.000Z");
        PromoCodesEntity promoCodesEntity5 = new PromoCodesEntity( 0.85, "FAMILY15", "Provides a 15% discount for family bookings with more than 2 rooms", 2, "2024-04-03T00:00:00.000Z", "2024-05-15T00:00:00.000Z");
        PromoCodesEntity promoCodesEntity6 = new PromoCodesEntity( 0.9, "LASTMINUTE10", "Offers last-minute booking discounts of 10%", 1, "2024-04-02T00:00:00.000Z", "2024-04-20T00:00:00.000Z");

        promoCodesEntityList.add(promoCodesEntity1);
        promoCodesEntityList.add(promoCodesEntity2);
        promoCodesEntityList.add(promoCodesEntity3);
        promoCodesEntityList.add(promoCodesEntity4);
        promoCodesEntityList.add(promoCodesEntity5);
        promoCodesEntityList.add(promoCodesEntity6);

        promoCodesRepository.saveAll(promoCodesEntityList);


        return promoCodesRepository.findAll();

    }

    /**
     * Validates a promo code.
     * @param promoCode The promo code to validate.
     * @param roomCount The number of rooms.
     * @param startDate The start date of booking.
     * @param endDate The end date of booking.
     * @return The validated promo code entity, or null if not valid.
     */


    public PromoCodesEntity validateCode(String promoCode, int roomCount, String startDate, String endDate) {
        PromoCodesEntity promoCodesEntity = promoCodesRepository.findById(promoCode).orElse(null);

        if (promoCodesEntity == null) {
            // Promo code not found in the repository
            return null;
        }

        String promoCodeStartDate = promoCodesEntity.getPromoCodeStartDate();
        String promoCodeEndDate = promoCodesEntity.getPromoCodeEndDate();

        // Validate if startDate and endDate in parameters should lie between the range of startdate and enddate of promocodeEntity
        if (!isDateWithinRange(startDate, promoCodeStartDate, promoCodeEndDate) ||
                !isDateWithinRange(endDate, promoCodeStartDate, promoCodeEndDate)) {
            return null;
        }

        int minimumDaysOfStay = promoCodesEntity.getMinimumNoOfDays();
        if (minimumDaysOfStay > lengthOfStay(startDate, endDate)) {
            return null;
        }

        if ("FAMILY10".equals(promoCode)) {
            if(roomCount < 3) return null;
        } else if ("LASTMINUTE10".equals(promoCode)) {
            // Validate if startDate is at most 10 days greater than the current date
            if (!isStartDateValid(startDate, 10)) {
                return null;
            }
        }

        return promoCodesEntity;
    }

    // Helper method to check if a date is within a specified range
    private boolean isDateWithinRange(String date, String startDate, String endDate) {
        LocalDate parsedDate = LocalDate.parse(date.substring(0, 10), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDate parsedStartDate = LocalDate.parse(startDate.substring(0, 10), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDate parsedEndDate = LocalDate.parse(endDate.substring(0, 10), DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        return !parsedDate.isBefore(parsedStartDate) && !parsedDate.isAfter(parsedEndDate);
    }

    // Helper method to check if startDate is at most 'days' greater than the current date
    private boolean isStartDateValid(String startDate, int days) {
        LocalDate parsedStartDate = LocalDate.parse(startDate.substring(0, 10), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDate currentDate = LocalDate.now();
        LocalDate maxStartDate = currentDate.plusDays(days);

        return !parsedStartDate.isAfter(maxStartDate);
    }

    // Helper method to calculate the length of stay
    private long lengthOfStay(String startDateStr, String endDateStr) {
        // Parse the strings into LocalDate objects
        LocalDate startDate = LocalDate.parse(startDateStr.substring(0, 10), DateTimeFormatter.ofPattern("yyyy-MM-dd")); // Extracting only the date part
        LocalDate endDate = LocalDate.parse(endDateStr.substring(0, 10), DateTimeFormatter.ofPattern("yyyy-MM-dd")); // Extracting only the date part

        // Find the length of stay in days
        return ChronoUnit.DAYS.between(startDate, endDate) + 1; // Including the end date
    }
}
