package com.triple.travelmanage.common.fixture.travel;

import com.triple.travelmanage.travel.adapter.out.persistence.TravelEntity;
import java.time.LocalDate;

public class TravelEntityFixture {

  public static TravelEntity 첫번째여행지(){
    return TravelEntity.builder()
        .userId(1L)
        .cityId(1L)
        .startDate(LocalDate.of(2022, 12, 20))
        .endDate(LocalDate.of(2022, 12, 22))
        .build();
  }

}
