package com.triple.travelmanage.common.fixture.city;

import com.triple.travelmanage.city.adapter.out.persistence.CityEntity;

public class CityEntityFixture {

  public static CityEntity 첫번째_도시() {
    return CityEntity.builder()
        .cityName("서울")
        .country("대한민국")
        .build();
  }


}
