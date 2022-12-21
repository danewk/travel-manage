package com.triple.travelmanage.city.application.service;

import static com.triple.travelmanage.common.fixture.city.CityEntityFixture.서울_도시_엔티티;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

import com.triple.travelmanage.city.application.port.in.CityInfo;
import com.triple.travelmanage.city.application.port.out.GetCityPort;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GetCityServiceTest {

  @Mock
  GetCityPort getCityPort;

  @InjectMocks
  GetCityService getCityService;

  @DisplayName("단건 도시 조회")
  @Test
  void getCity(){
    //given
    given(getCityPort.getCityById(1L)).willReturn(서울_도시_엔티티().toDomain());

    //when
    CityInfo city = getCityService.getCity(1L);

    //then
    assertEquals("서울",city.cityName());

  }

}