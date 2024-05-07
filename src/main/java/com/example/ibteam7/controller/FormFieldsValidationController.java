package com.example.ibteam7.controller;

import com.example.ibteam7.config.Constants;
import com.example.ibteam7.dto.BillingInfoValidationDto;
import com.example.ibteam7.dto.PaymentInfoValidationDto;
import com.example.ibteam7.dto.TravelerInfoValidationDto;
import com.example.ibteam7.service.FormFieldsValidationService;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin(origins = Constants.FRONTEND_URL)
@RequestMapping(Constants.REQUEST_MAPPING)
public class FormFieldsValidationController {

    @Autowired
    private FormFieldsValidationService formFieldsValidationService;

    @PostMapping("/validatetravelerinfo")
    public String travelerInfoValidation(@RequestBody TravelerInfoValidationDto travelerInfoValidationDto) {

        return formFieldsValidationService.validateTravelerInfo(travelerInfoValidationDto);
    }


    @PostMapping("/validatebillinginfo")
    public String billingInfoValidation(@RequestBody BillingInfoValidationDto billingInfoValidationDto) {

        return formFieldsValidationService.validateBillingInfo(billingInfoValidationDto);
    }


    @PostMapping("/validatepaymentinfo")
    public String paymentInfoValidation(@RequestBody PaymentInfoValidationDto paymentInfoValidationDto) {

        return formFieldsValidationService.validatePaymentInfo(paymentInfoValidationDto);
    }



}
