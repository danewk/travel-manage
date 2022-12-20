package com.triple.travelmanage.common.fixture.city;

import com.triple.travelmanage.city.application.port.in.command.CityCreateCommand;

public class CityCreateCommandFixture {

  public static CityCreateCommand 도시생성커맨드(){
    return CityCreateCommand.builder()
        .cityName("부산")
        .build();
  }

}
