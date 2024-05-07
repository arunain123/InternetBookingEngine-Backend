package com.example.ibteam7.service;

import com.example.ibteam7.dto.RoomDto;
import com.example.ibteam7.utility.GraphqlQuery;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Service class for managing rooms.
 */
@Service
public class RoomService {

    private static final String QUERY = "{ listRoomRates(take: 1500, orderBy: {date: ASC}) { date basic_nightly_rate room_rate_id } }";

    @Autowired
    private GraphqlQuery graphqlQuery;

    /**
     * Retrieves all rooms from the GraphQL API.
     *
     * @return List of RoomDto objects representing the rooms.
     */
    public List<RoomDto> findAllRooms() {
        ResponseEntity<JsonNode> allRatesResponse = graphqlQuery.executeGraphQLQueryHelper(QUERY);
        JsonNode body = allRatesResponse.getBody();

        List<RoomDto> rooms = extractRooms(body);
        return findRoomsWithMinimumRate(rooms);
    }

    /**
     * Extracts RoomDto objects from the JSON response.
     *
     * @param body The JSON response body.
     * @return List of RoomDto objects extracted from the response.
     */
    private List<RoomDto> extractRooms(JsonNode body) {
        JsonNode listRoomRates = body.path("data").path("listRoomRates");
        List<RoomDto> rooms = new ArrayList<>();
        for (JsonNode roomRate : listRoomRates) {
            RoomDto room = extractRoom(roomRate);
            rooms.add(room);
        }
        return rooms;
    }

    /**
     * Extracts a RoomDto object from a JSON node representing room data.
     *
     * @param roomRate JSON node containing room data.
     * @return RoomDto object extracted from the JSON node.
     */
    private RoomDto extractRoom(JsonNode roomRate) {
        String date = roomRate.path("date").asText();
        int basicNightlyRate = roomRate.path("basic_nightly_rate").asInt();
        int roomRateId = roomRate.path("room_rate_id").asInt();
        RoomDto room = new RoomDto();
        room.setRoomRateId(roomRateId);
        room.setDate(date);
        room.setBasicNightlyRate(basicNightlyRate);
        return room;
    }

    /**
     * Finds rooms with the minimum nightly rate for each date.
     *
     * @param rooms List of RoomDto objects.
     * @return List of RoomDto objects representing rooms with the minimum nightly rate for each date.
     */
    private List<RoomDto> findRoomsWithMinimumRate(List<RoomDto> rooms) {
        Map<String, List<RoomDto>> roomsByDate = groupRoomsByDate(rooms);
        Map<String, Integer> minNightlyRateByDate = findMinNightlyRateByDate(roomsByDate);
        return filterRoomsByMinNightlyRate(roomsByDate, minNightlyRateByDate);
    }

    /**
     * Groups rooms by date.
     *
     * @param rooms List of RoomDto objects.
     * @return Map where keys are dates and values are lists of RoomDto objects for each date.
     */
    private Map<String, List<RoomDto>> groupRoomsByDate(List<RoomDto> rooms) {
        return rooms.stream()
                .collect(Collectors.groupingBy(RoomDto::getDate));
    }

    /**
     * Finds the minimum nightly rate for each date.
     *
     * @param roomsByDate Map where keys are dates and values are lists of RoomDto objects for each date.
     * @return Map where keys are dates and values are the minimum nightly rate for each date.
     */
    private Map<String, Integer> findMinNightlyRateByDate(Map<String, List<RoomDto>> roomsByDate) {
        return roomsByDate.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> e.getValue().stream()
                                .mapToInt(RoomDto::getBasicNightlyRate)
                                .min()
                                .orElse(0)
                ));
    }

    /**
     * Filters rooms to include only those with the minimum nightly rate for each date.
     *
     * @param roomsByDate         Map where keys are dates and values are lists of RoomDto objects for each date.
     * @param minNightlyRateByDate Map where keys are dates and values are the minimum nightly rate for each date.
     * @return List of RoomDto objects representing rooms with the minimum nightly rate for each date.
     */
    private List<RoomDto> filterRoomsByMinNightlyRate(Map<String, List<RoomDto>> roomsByDate, Map<String, Integer> minNightlyRateByDate) {
        return roomsByDate.values().stream()
                .flatMap(List::stream)
                .filter(room -> room.getBasicNightlyRate() == minNightlyRateByDate.get(room.getDate()))
                .sorted(Comparator.comparing(RoomDto::getDate))
                .collect(Collectors.toList());
    }
}
