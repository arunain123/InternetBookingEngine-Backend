package com.example.ibteam7.controller;

import com.example.ibteam7.config.Constants;
import com.example.ibteam7.dto.PromoValidationResponseDto;
import com.example.ibteam7.dto.PromotionsDto;
import com.example.ibteam7.entity.PromoCodesEntity;
import com.example.ibteam7.service.PromoCodesService;
import com.example.ibteam7.service.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller class for handling promotions and promo codes.
 */
@RestController
@CrossOrigin(origins = Constants.FRONTEND_URL)
@RequestMapping(Constants.REQUEST_MAPPING)
public class PromotionController {

    @Autowired
    private PromotionService promotionService;

    @Autowired
    private PromoCodesService promoCodesService;



    /**
     * Get all promo codes.
     * @return List of PromoCodesEntity containing details of all promo codes.
     */
    @GetMapping("/promocodes")
    public List<PromoCodesEntity> getAllPromoCodes(){
        return promoCodesService.getPromoCodes();
    }

    /**
     * Validate a promo code.
     * @param promoCode The promo code to validate.
     * @param roomCount The number of rooms.
     * @param startDate The start date.
     * @param endDate The end date.
     * @return PromoCodesEntity if the promo code is valid, otherwise null.
     */
    @GetMapping("/validatepromo")
    public PromoValidationResponseDto validatePromoCode(@RequestParam String promoCode,
                                                        @RequestParam String roomCount,
                                                        @RequestParam String startDate,
                                                        @RequestParam String endDate) {
        PromoCodesEntity promoCodesEntity = promoCodesService.validateCode(promoCode, Integer.parseInt(roomCount), startDate, endDate);
        if (promoCodesEntity == null) {
            return new PromoValidationResponseDto(null, "Invalid promo code");
        } else {
            return new PromoValidationResponseDto(promoCodesEntity, "Promo code is valid");
        }
    }
}
