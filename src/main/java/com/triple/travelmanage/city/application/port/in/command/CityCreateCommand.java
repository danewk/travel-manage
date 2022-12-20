package com.triple.travelmanage.city.application.port.in.command;

import com.triple.travelmanage.city.adapter.in.web.CityCreateRequestDto;
import com.triple.travelmanage.city.domain.City;
import lombok.Builder;

@Builder
public record CityCreateCommand(
    String cityName,
    String country
) {

  public static CityCreateCommand of(CityCreateRequestDto requestDto) {
    return CityCreateCommand.builder()
        .cityName(requestDto.cityName())
        .country(requestDto.country())
        .build();
  }

  public City toDomain(){
    return City.builder()
        .cityName(cityName)
        .country(country)
        .build();
  }
}
