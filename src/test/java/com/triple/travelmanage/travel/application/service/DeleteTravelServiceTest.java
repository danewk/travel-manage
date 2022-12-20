package com.triple.travelmanage.travel.application.service;

import static com.triple.travelmanage.common.fixture.travel.TravelDomainFixture.첫번째여행지도메인;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

import com.triple.travelmanage.travel.application.port.out.DeleteTravelPort;
import com.triple.travelmanage.travel.application.port.out.GetTravelPort;
import com.triple.travelmanage.travel.domain.Travel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DeleteTravelServiceTest {

  @Mock
  GetTravelPort getTravelPort;

  @Mock
  DeleteTravelPort deleteTravelPort;

  @InjectMocks
  DeleteTravelService deleteTravelService;

  @DisplayName("여행 삭제 서비스")
  @Test
  void delete_travel(){
    Travel travel = 첫번째여행지도메인();
    given(getTravelPort.getTravelById(anyLong())).willReturn(travel);

    deleteTravelService.deleteTravel(travel.getId());

    assertThat(travel.getDeletedAt()).isNotNull();

  }

}