package com.triple.travelmanage.common.fixture.city;

import com.triple.travelmanage.city.adapter.out.persistence.CityEntity;

public class CityEntityFixture {

  public static CityEntity 서울_도시_엔티티() {
    return CityEntity.builder()
        .cityName("서울")
        .country("대한민국")
        .build();
  }

  public static CityEntity 경주_도시_엔티티() {
    return CityEntity.builder()
        .cityName("경주")
        .country("대한민국")
        .build();
  }

  public static CityEntity 부산_도시_엔티티() {
    return CityEntity.builder()
        .cityName("부산")
        .country("대한민국")
        .build();
  }

  public static CityEntity 제주도_도시_엔티티() {
    return CityEntity.builder()
        .cityName("제주도")
        .country("대한민국")
        .build();
  }


}
