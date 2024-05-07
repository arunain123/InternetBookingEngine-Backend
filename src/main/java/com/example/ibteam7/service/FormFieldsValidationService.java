package com.example.ibteam7.service;

import com.example.ibteam7.dto.BillingInfoValidationDto;
import com.example.ibteam7.dto.PaymentInfoValidationDto;
import com.example.ibteam7.dto.TravelerInfoValidationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class FormFieldsValidationService {



    public String validateTravelerInfo(TravelerInfoValidationDto travelerInfoValidationDto) {

        String phoneNo=travelerInfoValidationDto.getPhoneNo();
        String emailId=travelerInfoValidationDto.getEmailId();
        if (phoneNo != null && !phoneNo.isEmpty() && !isValidPhoneNumber(phoneNo)) {
            return "Invalid phone number";
        }

        if (emailId != null && !emailId.isEmpty() && !isValidEmail(emailId)) {
            return "Invalid email ID";
        }

        return "Valid traveler information";
    }

    public String validateBillingInfo(BillingInfoValidationDto billingInfoValidationDto) {
        String phoneNo=billingInfoValidationDto.getPhoneNo();
        String emailId=billingInfoValidationDto.getEmailId();
        if (phoneNo != null && !phoneNo.isEmpty() && !isValidPhoneNumber(phoneNo)) {
            return "Invalid phone number";
        }

        if (emailId != null && !emailId.isEmpty() && !isValidEmail(emailId)) {
            return "Invalid email ID";
        }

        return "Valid billing information";
    }


    public String validatePaymentInfo(PaymentInfoValidationDto paymentInfoValidationDto) {
        return "Valid payment info";
    }


    private boolean isValidPhoneNumber(String phoneNo) {
        // Regular expression for a simple 10-digit phone number
        String phonePattern = "^\\d{10}$";
        return phoneNo.matches(phonePattern);
    }

    private boolean isValidEmail(String email) {
        // Regular expression for a basic email validation
        String emailPattern = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,}$";
        return email.matches(emailPattern);
    }
}
