package com.triple.travelmanage.city.application.port.in.command;

import com.triple.travelmanage.city.adapter.in.web.CityUpdateRequestDto;
import com.triple.travelmanage.city.domain.City;
import lombok.Builder;

@Builder
public record CityUpdateCommand(
    String cityName,
    String country,
    Long cityId
) {

  public static CityUpdateCommand of(CityUpdateRequestDto requestDto, Long cityId) {
    return CityUpdateCommand.builder()
        .cityName(requestDto.cityName())
        .country(requestDto.country())
        .cityId(cityId)
        .build();
  }

  public City toDomain(){
    return City.builder()
        .cityName(cityName)
        .country(country)
        .build();
  }
}
