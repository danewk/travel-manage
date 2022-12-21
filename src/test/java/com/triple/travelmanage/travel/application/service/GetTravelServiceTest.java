package com.triple.travelmanage.travel.application.service;

import static com.triple.travelmanage.common.fixture.travel.TravelDomainFixture.두번째여행지도메인;
import static com.triple.travelmanage.common.fixture.travel.TravelDomainFixture.세번째여행지도메인;
import static com.triple.travelmanage.common.fixture.travel.TravelDomainFixture.첫번째여행지도메인;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

import com.triple.travelmanage.city.application.port.in.CityInfo;
import com.triple.travelmanage.travel.application.port.in.TravelInfo;
import com.triple.travelmanage.travel.application.port.out.GetTravelCityInfoPort;
import com.triple.travelmanage.travel.application.port.out.GetTravelPort;
import com.triple.travelmanage.travel.domain.Travel;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GetTravelServiceTest {

  @Mock
  GetTravelPort getTravelPort;

  @Mock
  GetTravelCityInfoPort getTravelCityInfoPort;

  @InjectMocks
  GetTravelService getTravelService;

  @DisplayName("단건 여행 조회")
  @Test
  void getTravel() {
    //given
    given(getTravelPort.getTravelById(anyLong())).willReturn(첫번째여행지도메인());

    //when
    TravelInfo travel = getTravelService.getTravel(1L);

    assertEquals(1L, travel.cityInfo().id());


  }

  @DisplayName("사용자가 등록한 전체 여행 조회")
  @Test
  void getTravels() {

    CityInfo cityInfo = CityInfo.builder()
        .id(1L)
        .country("대한민국")
        .cityName("강릉")
        .createdAt(LocalDateTime.now().minusDays(10L))
        .build();

    List<Travel> travelList = List.of(첫번째여행지도메인(), 두번째여행지도메인(), 세번째여행지도메인());
    given(getTravelPort.getTravelsByUserId(anyLong())).willReturn(travelList);
    given(getTravelCityInfoPort.getCityById(anyLong())).willReturn(cityInfo);

    List<TravelInfo> travelInfos = getTravelService.getTravelsByUserId(1L);

    assertThat(travelInfos.size()).isEqualTo(3);
    assertThat(travelInfos.get(0).cityInfo().cityName()).isEqualTo("강릉");

  }

}