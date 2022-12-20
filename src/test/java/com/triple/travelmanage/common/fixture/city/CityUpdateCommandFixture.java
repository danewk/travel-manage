package com.triple.travelmanage.common.fixture.city;

import com.triple.travelmanage.city.application.port.in.command.CityUpdateCommand;

public class CityUpdateCommandFixture {

  public static CityUpdateCommand 도시업데이트커맨드(){
    return CityUpdateCommand.builder()
        .cityId(1L)
        .cityName("경주")
        .country("대한민국")
        .build();
  }

}
