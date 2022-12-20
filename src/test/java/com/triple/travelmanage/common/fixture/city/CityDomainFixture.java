package com.triple.travelmanage.common.fixture.city;

import com.triple.travelmanage.city.domain.City;

public class CityDomainFixture {
  public static City 첫번째도시도메인(){
    return City.builder()
        .id(1L)
        .country("서울")
        .cityName("부산")
        .build();
  }
}
