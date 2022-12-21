package com.triple.travelmanage.common.fixture.city;

import com.triple.travelmanage.city.adapter.out.persistence.CitySearchHistoryEntity;
import com.triple.travelmanage.city.domain.CitySearchHistory;
import java.time.LocalDateTime;

public class CitySearchHistoryDomainFixture {
  public static CitySearchHistory 유저1_도시1_조회_내역_도메인(){
    return CitySearchHistory.builder()
        .id(1L)
        .cityId(1L)
        .userId(1L)
        .count(10L)
        .createdAt(LocalDateTime.now().minusDays(1))
        .build();
  }

  public static CitySearchHistory 유저1_도시1_조회_내역_1증가_도메인(){
    return CitySearchHistory.builder()
        .id(1L)
        .cityId(1L)
        .userId(1L)
        .count(11L)
        .createdAt(LocalDateTime.now().minusDays(1))
        .build();
  }

  public static CitySearchHistory 유저1_도시1_조회후_내역은_존재하지않는_도메인(){
    return CitySearchHistory.builder()
        .build();
  }

  public static CitySearchHistory 유저1_도시1_조회후_내역은_존재하지않아_새로생성된_도메인(){
    return CitySearchHistory.builder()
        .count(1L)
        .userId(1L)
        .cityId(1L)
        .id(1L)
        .build();
  }
}
