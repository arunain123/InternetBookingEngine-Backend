package com.example.ibteam7.service;

import com.example.ibteam7.dto.*;
import com.example.ibteam7.mapper.RoomDetailsMapper;
import com.example.ibteam7.utility.GraphqlQuery;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Service class to fetch room type details
 * and calculate the minimum room count across all dates for each room type
 */
@Service
public class RoomTypeDetailsService {

//    @Autowired
//    private RoomDetails roomDetails;

    @Autowired
    private PromotionService promotionService;

    @Autowired
    private RoomDetailsMapper roomDetailsMapper;

    @Autowired
    private RoomTypePriceService roomTypePriceService;

    @Autowired
    private RatingService ratingService;

    @Autowired
    public GraphqlQuery graphqlQuery;

    /**
     * Retrieves all room details for a given list of room type details.
     *
     * @param allRoomTypeDetails List of RoomTypeDetailsDto representing room type details.
     * @return List of RoomDetailsResponseDto representing room details.
     */
    public List<RoomDetailsResponseDto> findAllRoomDetails(List<RoomTypeDetailsDto> allRoomTypeDetails, String startDate, String endDate, String property, List<String> roomType, List<String> bedType, int sort, int roomCount, int priceLessThan) {
        Map<String, Integer> minRoomTypeCountMap = getMinRoomTypeCountMapFromGraphQL(allRoomTypeDetails, startDate, endDate, property);
        Map<String, List<DatePricePairDto>> RoomPriceMapList = new HashMap<>();
        Map<String, Double> avgPriceMap = roomTypePriceService.findAllPrices(RoomPriceMapList, startDate, endDate, property);

        List<PromotionsDto> promotionsDtoList = promotionService.findAllPromotions(startDate, endDate);
        Map<String,Double> ratingMap = ratingService.findallRatings();
        return roomDetailsMapper.mapToResponseDto(minRoomTypeCountMap, avgPriceMap, allRoomTypeDetails, promotionsDtoList, RoomPriceMapList, roomType, bedType, sort, roomCount, priceLessThan,ratingMap);
    }

    /**
     * Retrieves the minimum room count across all dates for each room type from the GraphQL API.
     *
     * @param allRoomTypeDetails List of RoomTypeDetailsDto representing room type details.
     * @return Map where keys are room type names and values are the minimum room count for each room type.
     */
    private Map<String, Integer> getMinRoomTypeCountMapFromGraphQL(List<RoomTypeDetailsDto> allRoomTypeDetails, String startDate, String endDate, String property) {
        String requestBody = buildGraphQLQuery(startDate, endDate, property);
        ResponseEntity<JsonNode> allRoomsResponse = graphqlQuery.executeGraphQLQueryHelper(requestBody);
        return extractRoomTypeCountMap(allRoomsResponse.getBody().get("data").get("listRoomAvailabilities"), allRoomTypeDetails);
    }

    /**
     * Builds the GraphQL query to retrieve room availabilities.
     *
     * @return GraphQL query string.
     */
    private String buildGraphQLQuery(String startDate, String endDate, String property) {
//        String startDate = roomDetails.getStartDate();
//        String endDate = roomDetails.getEndDate();
//        String property = roomDetails.getProperty();
        return String.format("{ listRoomAvailabilities( orderBy: { date: ASC } where: { AND: [ { property_id: { equals:" + property + "} }, { OR: [ { booking_id: { equals: 0 } }, { booking: { booking_status: { status: { equals: \\\"CANCELED\\\" } } } } ] }, { date: {gte: \\\"%s\\\" , lte: \\\"%s\\\"} } ] } take: 2000 ) { date room { room_id room_type { area_in_square_feet double_bed max_capacity room_type_id room_type_name single_bed } } } }", startDate, endDate);
    }

    /**
     * Extracts room type count map from the GraphQL response.
     *
     * @param listRoomAvailabilities JsonNode representing room availabilities.
     * @param allRoomTypeDetails     List of RoomTypeDetailsDto representing all room type details.
     * @return Map where keys are room type names and values are the room count for each room type.
     */
    private Map<String, Integer> extractRoomTypeCountMap(JsonNode listRoomAvailabilities, List<RoomTypeDetailsDto> allRoomTypeDetails) {
        Map<String, Integer> roomTypeDateCountMap = new HashMap<>();
        for (JsonNode roomAvailability : listRoomAvailabilities) {
            RoomTypeDetailsDto roomTypeDetailsDto = extractRoomTypeDetails(roomAvailability, allRoomTypeDetails);
            updateRoomTypeDateCountMap(roomTypeDateCountMap, roomTypeDetailsDto);
        }
        return calculateMinRoomTypeCountMap(roomTypeDateCountMap);
    }

    /**
     * Extracts room type details from the GraphQL response.
     *
     * @param roomAvailability  JsonNode representing room availability.
     * @param allRoomTypeDetails List of RoomTypeDetailsDto representing all room type details.
     * @return RoomTypeDetailsDto representing room type details.
     */
    private RoomTypeDetailsDto extractRoomTypeDetails(JsonNode roomAvailability, List<RoomTypeDetailsDto> allRoomTypeDetails) {
        int area = roomAvailability.get("room").get("room_type").get("area_in_square_feet").asInt();
        int doubleBed = roomAvailability.get("room").get("room_type").get("double_bed").asInt();
        int maxCapacity = roomAvailability.get("room").get("room_type").get("max_capacity").asInt();
        String roomTypeName = roomAvailability.get("room").get("room_type").get("room_type_name").asText();
        int singleBed = roomAvailability.get("room").get("room_type").get("single_bed").asInt();
        String date = roomAvailability.get("date").asText();
        int roomId = roomAvailability.get("room").get("room_id").asInt();
        RoomTypeDetailsDto roomTypeDetailsDto = new RoomTypeDetailsDto();
        roomTypeDetailsDto.setArea(area);
        roomTypeDetailsDto.setDoubleBed(doubleBed);
        roomTypeDetailsDto.setMaxCapacity(maxCapacity);
        roomTypeDetailsDto.setRoomTypeName(roomTypeName);
        roomTypeDetailsDto.setSingleBed(singleBed);
        roomTypeDetailsDto.setRoomId(roomId);
        roomTypeDetailsDto.setDate(date);
        allRoomTypeDetails.add(roomTypeDetailsDto);
        return roomTypeDetailsDto;
    }

    /**
     * Updates the room type date count map.
     *
     * @param roomTypeDateCountMap Map representing room type date count.
     * @param roomTypeDetailsDto   RoomTypeDetailsDto representing room type details.
     */
    private void updateRoomTypeDateCountMap(Map<String, Integer> roomTypeDateCountMap, RoomTypeDetailsDto roomTypeDetailsDto) {
        String key = roomTypeDetailsDto.getRoomTypeName() + "#" + roomTypeDetailsDto.getDate().substring(0, 10);
        roomTypeDateCountMap.put(key, roomTypeDateCountMap.getOrDefault(key, 0) + 1);
    }

    /**
     * Calculates the minimum room count for each room type.
     *
     * @param roomTypeDateCountMap Map representing room type date count.
     * @return Map where keys are room type names and values are the minimum room count for each room type.
     */
    private Map<String, Integer> calculateMinRoomTypeCountMap(Map<String, Integer> roomTypeDateCountMap) {
        Map<String, Integer> minRoomTypeCountMap = new HashMap<>();
        for (Map.Entry<String, Integer> entry : roomTypeDateCountMap.entrySet()) {
            String key = entry.getKey();
            int count = entry.getValue();
            String roomType = key.split("#")[0];
            minRoomTypeCountMap.put(roomType, Math.min(count, minRoomTypeCountMap.getOrDefault(roomType, Integer.MAX_VALUE)));
        }
        return minRoomTypeCountMap;
    }
}