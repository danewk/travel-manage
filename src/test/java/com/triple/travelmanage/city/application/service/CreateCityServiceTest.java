package com.triple.travelmanage.city.application.service;

import static com.triple.travelmanage.common.fixture.city.CityCreateCommandFixture.도시생성커맨드;
import static com.triple.travelmanage.common.fixture.city.CityDomainFixture.첫번째도시도메인;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import com.triple.travelmanage.city.application.port.in.CityInfo;
import com.triple.travelmanage.city.application.port.in.command.CityCreateCommand;
import com.triple.travelmanage.city.application.port.out.CreateCityPort;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CreateCityServiceTest {

  @Mock
  CreateCityPort createCityPort;

  @InjectMocks
  CreateCityService createCityService;

  @DisplayName("도시 등록 기능")
  @Test
  void createCity(){

    given(createCityPort.create(any())).willReturn(첫번째도시도메인());


    //when
    CityCreateCommand command = 도시생성커맨드();
    CityInfo city = createCityService.createCity(command);

    //then
    assertThat(city).isNotNull();
    assertThat(city.cityName()).isEqualTo(command.cityName());

  }

}