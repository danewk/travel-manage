package com.triple.travelmanage.city.application.service;

import static com.triple.travelmanage.common.fixture.city.CitySearchHistoryDomainFixture.유저1_도시1_조회_내역_1증가_도메인;
import static com.triple.travelmanage.common.fixture.city.CitySearchHistoryDomainFixture.유저1_도시1_조회_내역_도메인;
import static com.triple.travelmanage.common.fixture.city.CitySearchHistoryDomainFixture.유저1_도시1_조회_내역_유효기간지난_도메인;
import static com.triple.travelmanage.common.fixture.city.CitySearchHistoryDomainFixture.유저1_도시1_조회후_내역은_존재하지않는_도메인;
import static com.triple.travelmanage.common.fixture.city.CitySearchHistoryDomainFixture.유저1_도시1_조회후_내역은_존재하지않아_새로생성된_도메인;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

import com.triple.travelmanage.city.application.port.in.command.SyncHistoryCommand;
import com.triple.travelmanage.city.application.port.out.CreateCitySearchHistoryPort;
import com.triple.travelmanage.city.application.port.out.DeleteCitySearchHistoryPort;
import com.triple.travelmanage.city.application.port.out.GetCitySearchHistoryPort;
import com.triple.travelmanage.city.application.port.out.UpdateCitySearchHistoryPort;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SyncCitySearchHistoryServiceTest {

  @Mock
  CreateCitySearchHistoryPort createCitySearchHistoryPort;

  @Mock
  GetCitySearchHistoryPort getCitySearchHistoryPort;

  @Mock
  DeleteCitySearchHistoryPort deleteCitySearchHistoryPort;

  @Mock
  UpdateCitySearchHistoryPort updateCitySearchHistoryPort;

  @InjectMocks
  SyncCitySearchHistoryService syncCitySearchHistoryService;

  @DisplayName("도시내역 조회수 업데이트")
  @Test
  void update_city_search_history(){
    given(getCitySearchHistoryPort.getSearchHistory(anyLong(),anyLong())).willReturn(유저1_도시1_조회_내역_도메인());
    given(updateCitySearchHistoryPort.updateCount(any())).willReturn(유저1_도시1_조회_내역_1증가_도메인());

    //when
    SyncHistoryCommand command = SyncHistoryCommand.of(1L, 1L);


    //then
    assertThat(syncCitySearchHistoryService.syncHistory(command)).isNotNull();
    assertThat(syncCitySearchHistoryService.syncHistory(command).getCount()).isEqualTo(11L);

  }

  @DisplayName("도시내역 조회수 신규 생성")
  @Test
  void create_city_search_history(){
    given(getCitySearchHistoryPort.getSearchHistory(anyLong(), anyLong())).willReturn(유저1_도시1_조회후_내역은_존재하지않는_도메인());
    given(createCitySearchHistoryPort.createSearchHistory(any())).willReturn(유저1_도시1_조회후_내역은_존재하지않아_새로생성된_도메인());

    //when
    SyncHistoryCommand command = SyncHistoryCommand.of(1L, 1L);

    //then
    assertThat(syncCitySearchHistoryService.syncHistory(command)).isNotNull();
    assertThat(syncCitySearchHistoryService.syncHistory(command).getCount()).isEqualTo(1L);
  }

  @DisplayName("도시조회 내역 유효기간이 지나 삭제 후 신규 생성")
  @Test
  void delete_create_city_search_history(){
    given(getCitySearchHistoryPort.getSearchHistory(anyLong(), anyLong())).willReturn(유저1_도시1_조회_내역_유효기간지난_도메인());
    given(createCitySearchHistoryPort.createSearchHistory(any())).willReturn(유저1_도시1_조회후_내역은_존재하지않아_새로생성된_도메인());

    //when
    SyncHistoryCommand command = SyncHistoryCommand.of(1L, 1L);

    //then
    assertThat(syncCitySearchHistoryService.syncHistory(command)).isNotNull();
    assertThat(syncCitySearchHistoryService.syncHistory(command).getCount()).isEqualTo(1L);
  }


}