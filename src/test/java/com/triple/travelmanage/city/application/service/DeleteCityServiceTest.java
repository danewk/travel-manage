package com.triple.travelmanage.city.application.service;

import static com.triple.travelmanage.common.fixture.city.CityDomainFixture.첫번째도시도메인;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

import com.triple.travelmanage.city.application.port.out.DeleteCityPort;
import com.triple.travelmanage.city.application.port.out.GetCityPort;
import com.triple.travelmanage.city.domain.City;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DeleteCityServiceTest {

  @Mock
  GetCityPort getCityPort;

  @Mock
  DeleteCityPort deleteCityPort;

  @InjectMocks
  DeleteCityService deleteCityService;

  @DisplayName("도시 삭제 서비스")
  @Test
  void delete_city(){
    City city = 첫번째도시도메인();

    given(getCityPort.getCityById(anyLong())).willReturn(city);

    deleteCityService.deleteCity(city.getId());

    assertThat(city.getDeletedAt()).isNotNull();

  }

}