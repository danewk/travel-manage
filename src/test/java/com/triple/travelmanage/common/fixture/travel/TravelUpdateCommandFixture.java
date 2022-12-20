package com.triple.travelmanage.common.fixture.travel;

import com.triple.travelmanage.travel.application.port.in.command.TravelCreateCommand;
import com.triple.travelmanage.travel.application.port.in.command.TravelUpdateCommand;
import java.time.LocalDate;

public class TravelUpdateCommandFixture {

  public static TravelUpdateCommand 여행지수정커맨드(){
    return TravelUpdateCommand.builder()
        .travelId(1L)
        .country("미국")
        .cityName("뉴욕")
        .userId(1L)
        .startDate(LocalDate.of(2022, 12, 20))
        .endDate(LocalDate.of(2022, 12, 22))
        .build();
  }

}
