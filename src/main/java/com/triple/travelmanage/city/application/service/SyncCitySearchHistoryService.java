package com.triple.travelmanage.city.application.service;

import com.triple.travelmanage.city.adapter.in.web.CityRetrieveRequestDto;
import com.triple.travelmanage.city.application.port.in.SyncCitySearchHistoryUseCase;
import com.triple.travelmanage.city.application.port.out.CreateCitySearchHistoryPort;
import com.triple.travelmanage.city.application.port.out.DeleteCitySearchHistoryPort;
import com.triple.travelmanage.city.application.port.out.GetCitySearchHistoryPort;
import com.triple.travelmanage.city.application.port.out.UpdateCitySearchHistoryPort;
import com.triple.travelmanage.city.domain.CitySearchHistory;
import com.triple.travelmanage.common.annotation.UseCase;
import java.time.LocalDateTime;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@UseCase
@Slf4j
@RequiredArgsConstructor
public class SyncCitySearchHistoryService implements SyncCitySearchHistoryUseCase {

  private final CreateCitySearchHistoryPort createCitySearchHistoryPort;
  private final GetCitySearchHistoryPort getCitySearchHistoryPort;
  private final DeleteCitySearchHistoryPort deleteCitySearchHistoryPort;
  private final UpdateCitySearchHistoryPort updateCitySearchHistoryPort;

  @Override
  public void syncHistory(Long cityId, CityRetrieveRequestDto requestDto) {
    CitySearchHistory savedHistory = getCitySearchHistoryPort.getSearchHistory(cityId, requestDto.userId());
    if (Objects.isNull(savedHistory.getId())) {
      createHistory(cityId, requestDto);
    } else {
      if (savedHistory.getCreatedAt().isBefore(LocalDateTime.now().minusDays(7))) {
        deleteHistory(cityId, requestDto, savedHistory);
      }
      updateHistory(savedHistory);
    }
  }

  private void deleteHistory(Long cityId, CityRetrieveRequestDto requestDto, CitySearchHistory savedHistory) {
    savedHistory.delete();
    deleteCitySearchHistoryPort.delete(savedHistory);
    createHistory(cityId, requestDto);
  }

  private void updateHistory(CitySearchHistory savedHistory) {
    savedHistory.increaseCount();
    updateCitySearchHistoryPort.updateCount(savedHistory);
  }

  private void createHistory(Long cityId, CityRetrieveRequestDto requestDto) {
    CitySearchHistory citySearchHistory = CitySearchHistory.builder()
        .count(1L)
        .userId(requestDto.userId())
        .cityId(cityId)
        .build();
    createCitySearchHistoryPort.createSearchHistory(citySearchHistory);
  }
}
