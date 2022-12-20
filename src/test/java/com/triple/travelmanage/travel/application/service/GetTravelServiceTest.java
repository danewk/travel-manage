package com.triple.travelmanage.travel.application.service;

import static com.triple.travelmanage.common.fixture.travel.TravelDomainFixture.첫번째여행지도메인;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

import com.triple.travelmanage.travel.application.port.in.TravelInfo;
import com.triple.travelmanage.travel.application.port.out.GetTravelPort;
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

  @InjectMocks
  GetTravelService getTravelService;

  @DisplayName("단건 여행 조회")
  @Test
  void getTravel(){
    //given
    given(getTravelPort.getTravelById(anyLong())).willReturn(첫번째여행지도메인());

    //when
    TravelInfo travel = getTravelService.getTravel(1L);

    assertEquals(1L, travel.cityInfo().id());


  }

}