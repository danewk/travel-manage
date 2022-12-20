package com.triple.travelmanage.city.application.port.in.command;

import lombok.Builder;

@Builder
public record CityRetrieveCommand(
    String cityName,
    String country
) {

  public static CityRetrieveCommand of(String cityName, String country){
    return CityRetrieveCommand.builder()
        .cityName(cityName)
        .country(country)
        .build();
  }

}
