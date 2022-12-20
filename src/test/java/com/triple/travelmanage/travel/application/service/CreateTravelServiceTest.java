package com.triple.travelmanage.travel.application.service;

import static com.triple.travelmanage.common.fixture.travel.TravelCreateCommandFixture.여행지등록커맨드;
import static com.triple.travelmanage.common.fixture.travel.TravelCreateCommandFixture.출발일오류여행지등록커맨드;
import static com.triple.travelmanage.common.fixture.travel.TravelDomainFixture.첫번째여행지도메인;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import com.triple.travelmanage.city.application.port.in.CityInfo;
import com.triple.travelmanage.travel.application.port.in.TravelInfo;
import com.triple.travelmanage.travel.application.port.out.CreateTravelPort;
import com.triple.travelmanage.travel.application.port.out.GetTravelCityInfoPort;
import com.triple.travelmanage.travel.exception.TravelException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CreateTravelServiceTest {

  @Mock
  CreateTravelPort createTravelPort;

  @Mock
  GetTravelCityInfoPort getTravelCityInfoPort;

  @InjectMocks
  CreateTravelService createTravelService;

  @DisplayName("여행지 등록 기능")
  @Test
  void create_travel() {

    CityInfo cityInfo = CityInfo.builder()
        .id(1L)
        .country("미국")
        .cityName("뉴욕")
        .build();

    given(getTravelCityInfoPort.getCityInfo(any(), any())).willReturn(cityInfo);
    given(createTravelPort.create(any())).willReturn(첫번째여행지도메인());

    TravelInfo travel = createTravelService.createTravel(여행지등록커맨드());

    assertThat(travel.cityId()).isEqualTo(1L);
  }

  @DisplayName("출발일이 종료일보다 늦은 경우 에러 발생")
  @Test
  void create_exception(){
    CityInfo cityInfo = CityInfo.builder()
        .id(1L)
        .country("미국")
        .cityName("뉴욕")
        .build();

    given(getTravelCityInfoPort.getCityInfo(any(), any())).willReturn(cityInfo);


    assertThatThrownBy(() -> createTravelService.createTravel(출발일오류여행지등록커맨드()))
        .isInstanceOf(IllegalStateException.class);

  }

}