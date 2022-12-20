package com.triple.travelmanage.city.adapter.in.web;

import jakarta.validation.constraints.NotNull;

public record CityRetrieveRequestDto(
    @NotNull
    Long userId
) {

}
