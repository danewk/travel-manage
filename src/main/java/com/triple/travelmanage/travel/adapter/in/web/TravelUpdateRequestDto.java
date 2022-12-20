package com.triple.travelmanage.travel.adapter.in.web;

import java.time.LocalDate;

public record TravelUpdateRequestDto(
    Long userId,
    String country,
    String cityName,
    LocalDate startDate,
    LocalDate endDate
) {

}
