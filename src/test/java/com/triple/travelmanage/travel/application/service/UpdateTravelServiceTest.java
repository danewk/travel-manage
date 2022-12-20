package com.triple.travelmanage.travel.application.service;

import static com.triple.travelmanage.common.fixture.travel.TravelDomainFixture.첫번째여행지도메인;
import static com.triple.travelmanage.common.fixture.travel.TravelUpdateCommandFixture.여행지수정커맨드;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

import com.triple.travelmanage.city.application.port.in.CityInfo;
import com.triple.travelmanage.travel.application.port.in.TravelInfo;
import com.triple.travelmanage.travel.application.port.in.command.TravelUpdateCommand;
import com.triple.travelmanage.travel.application.port.out.GetTravelCityInfoPort;
import com.triple.travelmanage.travel.application.port.out.GetTravelPort;
import com.triple.travelmanage.travel.application.port.out.UpdateTravelPort;
import com.triple.travelmanage.travel.domain.Travel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UpdateTravelServiceTest {

  @Mock
  GetTravelPort getTravelPort;

  @Mock
  GetTravelCityInfoPort getTravelCityInfoPort;

  @Mock
  UpdateTravelPort updateTravelPort;

  @InjectMocks
  UpdateTravelService updateTravelService;

  @DisplayName("여행 업데이트 서비스")
  @Test
  void update_travel() {

    CityInfo cityInfo = CityInfo.builder()
        .id(2L)
        .country("미국")
        .cityName("뉴욕")
        .build();

    TravelUpdateCommand command = 여행지수정커맨드();
    Travel savedTravel = 첫번째여행지도메인();

    given(getTravelPort.getTravelById(anyLong())).willReturn(savedTravel);
    given(getTravelCityInfoPort.getCityInfo(any(), any())).willReturn(cityInfo);

    TravelInfo travelInfo = updateTravelService.updateTravel(command);

    assertThat(travelInfo.cityInfo().id()).isEqualTo(2L);
  }
}