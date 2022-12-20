package com.triple.travelmanage.common.fixture.travel;

import com.triple.travelmanage.travel.application.port.in.command.TravelCreateCommand;
import java.time.LocalDate;

public class TravelCreateCommandFixture {

  public static TravelCreateCommand 여행지등록커맨드(){
    return TravelCreateCommand.builder()
        .country("미국")
        .cityName("뉴욕")
        .userId(1L)
        .startDate(LocalDate.of(2022, 12, 20))
        .endDate(LocalDate.of(2022, 12, 22))
        .build();
  }

  public static TravelCreateCommand 출발일오류여행지등록커맨드(){
    return TravelCreateCommand.builder()
        .country("미국")
        .cityName("뉴욕")
        .userId(1L)
        .startDate(LocalDate.of(2022, 12, 24))
        .endDate(LocalDate.of(2022, 12, 22))
        .build();
  }

}
