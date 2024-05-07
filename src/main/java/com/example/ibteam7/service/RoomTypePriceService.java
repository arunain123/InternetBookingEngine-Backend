package com.example.ibteam7.service;

import com.example.ibteam7.dto.DatePricePairDto;
import com.example.ibteam7.dto.RoomDetails;
import com.example.ibteam7.utility.GraphqlQuery;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Service class to retrieve room type prices and calculate average prices.
 */
@Service
public class RoomTypePriceService {



    @Autowired
    public GraphqlQuery graphqlQuery;

    /**
     * Retrieves room prices from the GraphQL API and calculates average prices.
     *
     * @return Map where keys are room type names and values are average prices.
     */
    public Map<String, Double> findAllPrices(Map<String, List<DatePricePairDto>> RoomPriceMapList, String startDate, String endDate, String property) {

        String QUERY = "query MyQuery { listRoomRateRoomTypeMappings( where: {room_rate: {date: {gte: \\\"%s\\\" , lte: \\\"%s\\\"}}, room_type: {property_id: {equals: " + property + "}}} take: 2000) { room_rate { basic_nightly_rate date } room_type { room_type_name } } }";
        String requestBody = String.format(QUERY, startDate, endDate);

        ResponseEntity<JsonNode> allRoomsResponse = graphqlQuery.executeGraphQLQueryHelper(requestBody);
        JsonNode body = allRoomsResponse.getBody();

        Map<String, Integer> roomPriceMap = extractRoomPrices(body);
        return calculateAvgPrice(roomPriceMap, RoomPriceMapList);
    }

    /**
     * Extracts room prices from the GraphQL response.
     *
     * @param body JsonNode representing the GraphQL response body.
     * @return Map where keys are room type names with dates and values are room prices.
     */
    private Map<String, Integer> extractRoomPrices(JsonNode body) {
        JsonNode listRoomAvailabilities = body.get("data").get("listRoomRateRoomTypeMappings");
        Map<String, Integer> roomPriceMap = new HashMap<>();

        for (JsonNode roomAvailability : listRoomAvailabilities) {
            int basicNightlyRate = roomAvailability.get("room_rate").get("basic_nightly_rate").asInt();
            String date = roomAvailability.get("room_rate").get("date").asText();
            String roomTypeName = roomAvailability.get("room_type").get("room_type_name").asText();
            String key = roomTypeName + "#" + date.substring(0, 10);

            if (roomPriceMap.containsKey(key)) {
                int existingRate = roomPriceMap.get(key);
                roomPriceMap.put(key, Math.min(existingRate, basicNightlyRate));
            } else {
                roomPriceMap.put(key, basicNightlyRate);
            }
        }

        return roomPriceMap;
    }

    /**
     * Calculates average prices from the room price map.
     *
     * @param roomPriceMap Map where keys are room type names with dates and values are room prices.
     * @return Map where keys are room type names and values are average prices.
     */
    private Map<String, Double> calculateAvgPrice(Map<String, Integer> roomPriceMap, Map<String, List<DatePricePairDto>> RoomPriceMapList) {
        Map<String, Integer> totalPriceMap = calculateTotalPrice(roomPriceMap, RoomPriceMapList);
        Map<String, Integer> totalCountMap = calculateTotalCount(roomPriceMap);
        return calculateAveragePrice(totalPriceMap, totalCountMap);
    }

    /**
     * Calculates the total price for each room type.
     *
     * @param roomPriceMap Map where keys are room type names with dates and values are room prices.
     * @return Map where keys are room type names and values are total prices.
     */
    private Map<String, Integer> calculateTotalPrice(Map<String, Integer> roomPriceMap, Map<String, List<DatePricePairDto>> RoomPriceMapList) {
        Map<String, Integer> totalPriceMap = new HashMap<>();

        for (Map.Entry<String, Integer> entry : roomPriceMap.entrySet()) {
            String roomTypeName = entry.getKey().split("#")[0];
            String dateStr = entry.getKey().split("#")[1];
//            Date date = parseDate(dateStr); // Parse date string to Date object
            int price = entry.getValue();
            totalPriceMap.put(roomTypeName, totalPriceMap.getOrDefault(roomTypeName, 0) + price);



            // If the room type is already in the avgPriceMap, add the new date-price pair
            if (RoomPriceMapList.containsKey(roomTypeName)) {
                RoomPriceMapList.get(roomTypeName).add(new DatePricePairDto(dateStr, entry.getValue()));
            } else {
                // If the room type is not in the avgPriceMap, create a new list and add the date-price pair
                List<DatePricePairDto> priceList = new ArrayList<>();
                priceList.add(new DatePricePairDto(dateStr, entry.getValue()));
                RoomPriceMapList.put(roomTypeName, priceList);
            }
        }

        return totalPriceMap;
    }

    /**
     * Calculates the total count for each room type.
     *
     * @param roomPriceMap Map where keys are room type names with dates and values are room prices.
     * @return Map where keys are room type names and values are total counts.
     */
    private Map<String, Integer> calculateTotalCount(Map<String, Integer> roomPriceMap) {
        Map<String, Integer> totalCountMap = new HashMap<>();

        for (String key : roomPriceMap.keySet()) {
            String roomTypeName = key.split("#")[0];
            totalCountMap.put(roomTypeName, totalCountMap.getOrDefault(roomTypeName, 0) + 1);
        }

        return totalCountMap;
    }

    /**
     * Calculates the average price for each room type.
     *
     * @param totalPriceMap Map where keys are room type names and values are total prices.
     * @param totalCountMap Map where keys are room type names and values are total counts.
     * @return Map where keys are room type names and values are average prices.
     */
    private Map<String, Double> calculateAveragePrice(Map<String, Integer> totalPriceMap, Map<String, Integer> totalCountMap) {
        Map<String, Double> averagePriceMap = new HashMap<>();

        for (String roomTypeName : totalPriceMap.keySet()) {
            int totalPrice = totalPriceMap.get(roomTypeName);
            int totalCount = totalCountMap.get(roomTypeName);
            double averagePrice = (double) totalPrice / totalCount;
            averagePriceMap.put(roomTypeName, averagePrice);
        }

        return averagePriceMap;
    }
}