package com.example.ibteam7.service;

import com.example.ibteam7.utility.GraphqlQuery;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class ValidateBookingService {



    @Autowired
    private GraphqlQuery graphqlQuery;

    public List<Integer> validateBooking(String roomTypeName, String startDate, String endDate, int countRoom, int propertyId) {

        String requestBody = buildGraphQLQuery(roomTypeName, startDate, endDate, propertyId);
        ResponseEntity<JsonNode> allRoomsResponse = graphqlQuery.executeGraphQLQueryHelper(requestBody);
        Map<Integer, List<Integer>> roomIdAvailabilityMap = new HashMap<>();
        Map<Integer, Integer> roomIdCountMap = extractRoomIdCountMap(allRoomsResponse.getBody().get("data").get("listRoomAvailabilities"), roomIdAvailabilityMap);
        long dateDifference = calculateDateDifference(startDate, endDate);
//        if (isBookingValid(roomIdCountMap, countRoom, dateDifference)) {
//            return getAvailabilityIds(roomIdCountMap, countRoom, roomIdAvailabilityMap);
//        }

        List<Integer> validRoomIds = findValidIds(roomIdCountMap,dateDifference);
        if(validRoomIds.size()<countRoom) return Collections.emptyList();
        return getAvailabilityIds(validRoomIds,roomIdAvailabilityMap);
    }

    private String buildGraphQLQuery(String roomTypeName, String startDate, String endDate, int propertyId) {
        String property = String.valueOf(propertyId);
        return String.format("{ listRoomAvailabilities( orderBy: { date: ASC } where: { AND: [ { property_id: { equals:" + property + "} }, { OR: [ { booking_id: { equals: 0 } }, { booking: { booking_status: { status: { equals: \\\"CANCELED\\\" } } } } ] }, { date: {gte: \\\"%s\\\" , lte: \\\"%s\\\"} }  { room: { room_type: { room_type_name: { equals: \\\"%s\\\" } } } }  ] } take: 2000 ) { availability_id room { room_id room_type { room_type_id room_type_name } } } }", startDate, endDate,roomTypeName);
    }

    private Map<Integer, Integer> extractRoomIdCountMap(JsonNode listRoomAvailabilities, Map<Integer,List<Integer>> roomIdAvailabilityMap) {
//        System.out.println(listRoomAvailabilities);
        Map<Integer, Integer> roomIdCountMap = new HashMap<>();
        if (listRoomAvailabilities != null && listRoomAvailabilities.isArray()) {
            for (JsonNode roomAvailability : listRoomAvailabilities) {
                int availabilityId = roomAvailability.get("availability_id").asInt();
                JsonNode roomNode = roomAvailability.get("room");
                if (roomNode != null && roomNode.has("room_id")) {
                    int roomId = roomNode.get("room_id").asInt();
                    roomIdCountMap.put(roomId, roomIdCountMap.getOrDefault(roomId, 0) + 1);
                    if (!roomIdAvailabilityMap.containsKey(roomId)) {
                        roomIdAvailabilityMap.put(roomId, new ArrayList<>());
                    }
                    roomIdAvailabilityMap.get(roomId).add(availabilityId);
                }
            }
        }

        return roomIdCountMap;
    }


    public long calculateDateDifference(String startDate, String endDate) {
        LocalDate startDateTime = LocalDate.parse(startDate.substring(0,10));
        LocalDate endDateTime = LocalDate.parse(endDate.substring(0,10));
        return ChronoUnit.DAYS.between(startDateTime, endDateTime)+1;
    }



    private List<Integer> findValidIds(Map<Integer, Integer> roomIdCountMap, long dateDifference) {

        List<Integer> validRoomIds=new ArrayList<>();
        for (Integer roomId : roomIdCountMap.keySet()) {
            if (roomIdCountMap.get(roomId) >= dateDifference) {
                validRoomIds.add(roomId);
            }
        }
        return validRoomIds;
    }


    private List<Integer> getAvailabilityIds(List<Integer> validRoomIds, Map<Integer, List<Integer>> roomIdAvailabilityMap) {
        List<Integer> availabilityIds = new ArrayList<>();
        for (Integer roomId : validRoomIds) {
            List<Integer> roomAvailabilityIds = roomIdAvailabilityMap.get(roomId);
            if (roomAvailabilityIds != null) { // Check if the room ID exists in the map
                availabilityIds.addAll(roomAvailabilityIds);
            }
        }
        return availabilityIds;
    }

}
