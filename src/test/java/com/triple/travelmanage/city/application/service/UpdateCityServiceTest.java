package com.triple.travelmanage.city.application.service;

import static com.triple.travelmanage.common.fixture.city.CityDomainFixture.첫번째도시도메인;
import static com.triple.travelmanage.common.fixture.city.CityUpdateCommandFixture.도시업데이트커맨드;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

import com.triple.travelmanage.city.application.port.in.CityInfo;
import com.triple.travelmanage.city.application.port.in.command.CityUpdateCommand;
import com.triple.travelmanage.city.application.port.out.GetCityPort;
import com.triple.travelmanage.city.application.port.out.UpdateCityPort;
import com.triple.travelmanage.city.domain.City;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UpdateCityServiceTest {

  @Mock
  GetCityPort getCityPort;

  @Mock
  UpdateCityPort updateCityPort;

  @InjectMocks
  UpdateCityService updateCityService;

  @DisplayName("도시 업데이트 서비스")
  @Test
  void update_city(){
    CityUpdateCommand command = 도시업데이트커맨드();
    City savedCity = 첫번째도시도메인();

    given(getCityPort.getCityById(anyLong())).willReturn(savedCity);

    CityInfo cityInfo = updateCityService.updateCity(command);

    assertEquals("경주",savedCity.getCityName());
    assertEquals("경주",cityInfo.cityName());
  }


}