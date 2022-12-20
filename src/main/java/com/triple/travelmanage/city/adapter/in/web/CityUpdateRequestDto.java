package com.triple.travelmanage.city.adapter.in.web;

import jakarta.validation.constraints.NotNull;

public record CityUpdateRequestDto(
    @NotNull
    String cityName,
    @NotNull
    String country
) {

}
