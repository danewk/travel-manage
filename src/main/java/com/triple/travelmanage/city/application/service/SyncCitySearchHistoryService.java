package com.triple.travelmanage.city.application.service;

import com.triple.travelmanage.city.application.port.in.SyncCitySearchHistoryUseCase;
import com.triple.travelmanage.city.application.port.in.command.SyncHistoryCommand;
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
  public CitySearchHistory syncHistory(SyncHistoryCommand command) {
    CitySearchHistory savedHistory = getCitySearchHistoryPort.getSearchHistory(command.cityId(), command.userId());
    if (Objects.isNull(savedHistory.getId())) {
      return createHistory(command.cityId(), command.userId());
    } else {
      if (savedHistory.getCreatedAt().isBefore(LocalDateTime.now().minusDays(7))) {
        deleteHistory(savedHistory);
        return createHistory(command.cityId(), command.userId());
      }
      return updateHistory(savedHistory.getId());
    }
  }

  private void deleteHistory(CitySearchHistory savedHistory) {
    savedHistory.delete();
    deleteCitySearchHistoryPort.delete(savedHistory);
  }

  private CitySearchHistory updateHistory(Long id) {
    return updateCitySearchHistoryPort.updateCount(id);
  }

  private CitySearchHistory createHistory(Long cityId, Long userId) {
    CitySearchHistory citySearchHistory = CitySearchHistory.builder()
        .count(1L)
        .userId(userId)
        .cityId(cityId)
        .build();
    return createCitySearchHistoryPort.createSearchHistory(citySearchHistory);
  }
}
