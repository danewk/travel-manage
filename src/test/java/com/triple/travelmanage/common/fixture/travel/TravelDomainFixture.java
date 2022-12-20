package com.triple.travelmanage.common.fixture.travel;

import com.triple.travelmanage.travel.domain.Travel;
import java.time.LocalDate;

public class TravelDomainFixture {

  public static Travel 첫번째여행지도메인(){
    return Travel.builder()
        .id(1L)
        .userId(1L)
        .cityId(1L)
        .startDate(LocalDate.of(2022, 12, 20))
        .endDate(LocalDate.of(2022, 12, 22))
        .build();
  }
}
