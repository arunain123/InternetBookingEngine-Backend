package com.example.ibteam7.service;


import com.example.ibteam7.dto.*;
import com.example.ibteam7.entity.*;
import com.example.ibteam7.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;




@Service
public class UpdateBookingDataService {


    @Autowired
    private BillingInfoValidation billingInfoValidationDto;


    @Autowired
    private TravelerInfoValidation travelerInfoValidationDto;

    @Autowired
    private PaymentInfoValidation paymentInfoValidationDto;

    @Autowired
    private UniqueBookingRepository uniqueBookingRepository;

    @Autowired
    private TravelerInfoRepository travelerInfoRepository;

    @Autowired
    private PaymentInfoRepository paymentInfoRepository;

    @Autowired
    private BillingInfoRepository billingInfoRepository;

    @Autowired
    private UserBookingsRepository userBookingsRepository;

    @Autowired
    private RoomSummaryRepository roomSummaryRepository;

    public void updateBookingData(int bookingId, BookingRequestDto bookingRequestDto){
        updateBookingInfo(bookingId);

        updateTravelerInfo(bookingId, bookingRequestDto);
        updatePaymentInfo(bookingId, bookingRequestDto);
        updateBillingInfo(bookingId, bookingRequestDto);
        updateRoomSummary(bookingId, bookingRequestDto);
        updateUserBookings(bookingId, bookingRequestDto);
    }

    private void updateBookingInfo(int bookingId){
        // Create a new UniqueBookingEntity
        UniqueBookingEntity bookingEntity = new UniqueBookingEntity();
//        generate a random integer of 6 digits
        int bookingIdMapper = (int) (Math.random() * 1000000);
        bookingEntity.setBookingIdMapper(bookingIdMapper);
        bookingEntity.setBookingId(bookingId);
        // Save the new entity
        uniqueBookingRepository.save(bookingEntity);
    }

    private void updateTravelerInfo(int bookingId, BookingRequestDto bookingRequestDto){
        // Create a new TravelerInfoEntity
        TravelerInfoEntity travelerInfoEntity = new TravelerInfoEntity();


        // Set traveler information
        travelerInfoEntity.setFirstName(bookingRequestDto.getTravellerInfo().get("firstName").asText());
        travelerInfoEntity.setLastName(bookingRequestDto.getTravellerInfo().get("lastName").asText());
        travelerInfoEntity.setPhoneNo(bookingRequestDto.getTravellerInfo().get("phoneNo").asText());
        travelerInfoEntity.setEmailId(bookingRequestDto.getTravellerInfo().get("emailId").asText());

        UniqueBookingEntity uniqueBookingEntity=uniqueBookingRepository.findById(bookingId).orElse(null);
        travelerInfoEntity.setUniqueBookingEntity(uniqueBookingEntity);

        // Save the new entity
        travelerInfoRepository.save(travelerInfoEntity);
    }

    private void updatePaymentInfo(int bookingId, BookingRequestDto bookingRequestDto){
        // Create a new PaymentInfoEntity
        PaymentInfoEntity paymentInfoEntity = new PaymentInfoEntity();

        // Set payment information
        paymentInfoEntity.setCardNo(bookingRequestDto.getPaymentInfo().get("cardNo").asText());
        paymentInfoEntity.setExpiryMonth(bookingRequestDto.getPaymentInfo().get("expiryMonth").asText());
        paymentInfoEntity.setExpiryYear(bookingRequestDto.getPaymentInfo().get("expiryYear").asText());

        // Set UniqueBookingEntity in PaymentInfoEntity
        UniqueBookingEntity uniqueBookingEntity=uniqueBookingRepository.findById(bookingId).orElse(null);
        paymentInfoEntity.setUniqueBookingEntity(uniqueBookingEntity);

        // Save the new entity
        paymentInfoRepository.save(paymentInfoEntity);
    }

    private void updateBillingInfo(int bookingId, BookingRequestDto bookingRequestDto){
        // Create a new BillingInfoEntity
        BillingInfoEntity billingInfoEntity = new BillingInfoEntity();

        // Set billing information
        billingInfoEntity.setFirstName(bookingRequestDto.getBillingInfo().get("firstName").asText());
        billingInfoEntity.setLastName(bookingRequestDto.getBillingInfo().get("lastName").asText());
        billingInfoEntity.setMailingAddress1(bookingRequestDto.getBillingInfo().get("mailingAddress1").asText());
        billingInfoEntity.setMailingAddress2(bookingRequestDto.getBillingInfo().get("mailingAddress2").asText());
        billingInfoEntity.setCountry(bookingRequestDto.getBillingInfo().get("country").asText());
        billingInfoEntity.setState(bookingRequestDto.getBillingInfo().get("state").asText());
        billingInfoEntity.setCity(bookingRequestDto.getBillingInfo().get("city").asText());
        billingInfoEntity.setZip(bookingRequestDto.getBillingInfo().get("zip").asText());
        billingInfoEntity.setPhoneNo(bookingRequestDto.getBillingInfo().get("phoneNo").asText());
        billingInfoEntity.setEmailId(bookingRequestDto.getBillingInfo().get("emailId").asText());

        // Set UniqueBookingEntity in BillingInfoEntity
        UniqueBookingEntity uniqueBookingEntity=uniqueBookingRepository.findById(bookingId).orElse(null);
        billingInfoEntity.setUniqueBookingEntity(uniqueBookingEntity);

        // Save the new entity
        billingInfoRepository.save(billingInfoEntity);
    }

    private void updateRoomSummary(int bookingId, BookingRequestDto bookingRequestDto){
        // Create a new RoomSummaryEntity
        RoomSummaryEntity roomSummaryEntity = new RoomSummaryEntity();

        // Set room summary information
        roomSummaryEntity.setPromotionTitle(bookingRequestDto.getPromotionTitle());
        roomSummaryEntity.setPromotionDescription(bookingRequestDto.getPromotionDescription());
        roomSummaryEntity.setStartDate(bookingRequestDto.getCheckInDate());
        roomSummaryEntity.setEndDate(bookingRequestDto.getCheckOutDate());
        roomSummaryEntity.setTotalPrice(bookingRequestDto.getTotalPrice());

        // Set UniqueBookingEntity in RoomSummaryEntity
        UniqueBookingEntity uniqueBookingEntity=uniqueBookingRepository.findById(bookingId).orElse(null);
        roomSummaryEntity.setUniqueBookingEntity(uniqueBookingEntity);

        // Save the new entity
        roomSummaryRepository.save(roomSummaryEntity);
    }

    private void updateUserBookings(int bookingId, BookingRequestDto bookingRequestDto){
        // Create a new UniqueBookingEntity
        if(bookingRequestDto.getUserEmail()==null) return;

        UserBookingsEntity userBookingsEntity= new UserBookingsEntity();
        userBookingsEntity.setEmailId(bookingRequestDto.getUserEmail());

        UniqueBookingEntity uniqueBookingEntity=uniqueBookingRepository.findById(bookingId).orElse(null);
        userBookingsEntity.setUniqueBookingEntity(uniqueBookingEntity);
        userBookingsRepository.save(userBookingsEntity);
    }



}
