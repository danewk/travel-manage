package com.triple.travelmanage.common.fixture.travel;

import com.triple.travelmanage.travel.adapter.out.persistence.TravelEntity;
import java.time.LocalDate;

public class TravelEntityFixture {

  public static TravelEntity 사용자1_도시1_여행지_엔티티(){
    return TravelEntity.builder()
        .userId(1L)
        .cityId(1L)
        .startDate(LocalDate.of(2022, 12, 20))
        .endDate(LocalDate.of(2022, 12, 22))
        .build();
  }

  public static TravelEntity 사용자1_도시2_여행지_엔티티(){
    return TravelEntity.builder()
        .userId(1L)
        .cityId(2L)
        .startDate(LocalDate.of(2022, 12, 25))
        .endDate(LocalDate.of(2022, 12, 29))
        .build();
  }



}
