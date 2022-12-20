package com.triple.travelmanage.city.adapter.in.web;

import jakarta.validation.constraints.NotNull;

public record CityCreateRequestDto(
    @NotNull
    String cityName,
    @NotNull
    String country
) {

}
