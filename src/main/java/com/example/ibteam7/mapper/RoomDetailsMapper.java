package com.example.ibteam7.mapper;

import com.example.ibteam7.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Mapper class to convert RoomTypeDetailsDto objects to RoomDetailsResponseDto objects.
 */
@Component
public class RoomDetailsMapper {



    /**
     * Maps RoomTypeDetailsDto objects to RoomDetailsResponseDto objects.
     * @param countMap Map with room type as key and minimum count across all dates as value.
     * @param avgPriceMap Map with room type as key and average price as value.
     * @param roomDetailsList List of RoomTypeDetailsDto objects.
     * @param promotionsDtoList List of PromotionsDto objects.
     * @return List of RoomDetailsResponseDto objects.
     */
    public List<RoomDetailsResponseDto> mapToResponseDto(Map<String, Integer> countMap, Map<String, Double> avgPriceMap, List<RoomTypeDetailsDto> roomDetailsList, List<PromotionsDto> promotionsDtoList, Map<String, List<DatePricePairDto>> RoomPriceMapList, List<String> roomType, List<String> bedType, int sort, int roomCount, int priceLessThan, Map<String, Double> ratingMap) {
        Map<String, RoomTypeDetailsDto> uniqueRoomTypes = getUniqueRoomTypes(roomDetailsList);
        List<RoomDetailsResponseDto> responseDtoList = createResponseDtoList(countMap, avgPriceMap, uniqueRoomTypes, promotionsDtoList, RoomPriceMapList, ratingMap);
        System.out.println("this is responsedto list hkchwecveljhcvdwjhcvwjh");
        System.out.println(responseDtoList);
        responseDtoList = applyFilterAndSort(responseDtoList, roomType, bedType, sort, roomCount, priceLessThan);
        return responseDtoList;
    }

    private Map<String, RoomTypeDetailsDto> getUniqueRoomTypes(List<RoomTypeDetailsDto> roomDetailsList) {
        Map<String, RoomTypeDetailsDto> uniqueRoomTypes = new HashMap<>();
        for (RoomTypeDetailsDto roomDetail : roomDetailsList) {
            uniqueRoomTypes.putIfAbsent(roomDetail.getRoomTypeName(), roomDetail);
        }
        return uniqueRoomTypes;
    }

    private List<RoomDetailsResponseDto> createResponseDtoList(Map<String, Integer> countMap, Map<String, Double> avgPriceMap, Map<String, RoomTypeDetailsDto> uniqueRoomTypes, List<PromotionsDto> promotionsDtoList, Map<String, List<DatePricePairDto>> RoomPriceMapList, Map<String, Double> ratingMap) {
        List<RoomDetailsResponseDto> responseDtoList = new ArrayList<>();
        for (RoomTypeDetailsDto roomDetail : uniqueRoomTypes.values()) {
            String roomTypeName = roomDetail.getRoomTypeName();
            Integer count = countMap.getOrDefault(roomTypeName, 0);
            Double avgPrice = avgPriceMap.getOrDefault(roomTypeName, 0.0);
            List<DatePricePairDto> datePricePairDtoList = RoomPriceMapList.getOrDefault(roomTypeName, null);
            RoomDetailsResponseDto responseDto = new RoomDetailsResponseDto(
                    roomDetail.getDate(),
                    roomDetail.getRoomId(),
                    roomTypeName,
                    roomDetail.getMaxCapacity(),
                    roomDetail.getArea(),
                    roomDetail.getSingleBed(),
                    roomDetail.getDoubleBed(),
                    avgPrice,
                    count,
                    promotionsDtoList,
                    datePricePairDtoList,
                    ratingMap.get(roomTypeName)
            );
            responseDtoList.add(responseDto);
        }
        return responseDtoList;
    }

    private List<RoomDetailsResponseDto> applyFilterAndSort(List<RoomDetailsResponseDto> responseDtoList, List<String> roomTypeList, List<String> bedTypeList, int sort, int roomCount, int priceLessThan) {



        return responseDtoList.stream()
                .filter(dto -> {
                    if (roomTypeList.isEmpty()) return true;
                    String roomTypeName = dto.getRoomTypeName().toLowerCase();
                    for (String roomType : roomTypeList) {
                        String trimmedRoomType = roomType.trim().toLowerCase();
                        if (roomTypeName.contains(trimmedRoomType)) {
                            return true;
                        }
                    }
                    return false;
                })
                .filter(dto -> {
                    if ( bedTypeList.size()==1 && bedTypeList.get(0).equals("")) return true;


                    if((bedTypeList.contains("queen") && dto.getSingleBed() >= 1) ||
                            (bedTypeList.contains("king") && dto.getDoubleBed() >= 1)) return true;

                    return false;

                })
                .filter(dto -> dto.getAvgPrice() < priceLessThan &&
                        dto.getCount() >= roomCount )

                .sorted(getSortingComparator(sort))
                .collect(Collectors.toList());




    }

    private Comparator<RoomDetailsResponseDto> getSortingComparator(int sort) {
        if (sort == 1) {
            return Comparator.comparingDouble(RoomDetailsResponseDto::getAvgPrice);
        } else if (sort == 2) {
            return (dto1, dto2) -> Double.compare(dto2.getAvgPrice(), dto1.getAvgPrice());
        }
        return Comparator.comparing(RoomDetailsResponseDto::getDate); // Default sorting if no sort criteria specified
    }
}