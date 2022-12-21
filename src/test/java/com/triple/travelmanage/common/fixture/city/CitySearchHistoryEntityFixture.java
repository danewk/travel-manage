package com.triple.travelmanage.common.fixture.city;

import com.triple.travelmanage.city.adapter.out.persistence.CitySearchHistoryEntity;

public class CitySearchHistoryEntityFixture {
  public static CitySearchHistoryEntity 유저1_도시1_조회_내역_엔티티(){
    return CitySearchHistoryEntity.builder()
        .cityId(1L)
        .uniqueId(null)
        .userId(1L)
        .count(10L)
        .build();
  }

  public static CitySearchHistoryEntity 유저1_도시1_조회수1_엔티티(){
    return CitySearchHistoryEntity.builder()
        .cityId(1L)
        .uniqueId(null)
        .userId(1L)
        .count(1L)
        .build();
  }
}
