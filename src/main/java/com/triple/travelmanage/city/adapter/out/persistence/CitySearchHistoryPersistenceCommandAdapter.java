package com.triple.travelmanage.city.adapter.out.persistence;

import com.triple.travelmanage.city.application.port.out.CreateCitySearchHistoryPort;
import com.triple.travelmanage.city.application.port.out.DeleteCitySearchHistoryPort;
import com.triple.travelmanage.city.application.port.out.UpdateCitySearchHistoryPort;
import com.triple.travelmanage.city.domain.CitySearchHistory;
import com.triple.travelmanage.city.exception.CityException;
import com.triple.travelmanage.common.annotation.PersistenceAdapter;
import com.triple.travelmanage.common.exception.ErrorMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@PersistenceAdapter
@RequiredArgsConstructor
@Transactional
public class CitySearchHistoryPersistenceCommandAdapter implements CreateCitySearchHistoryPort, DeleteCitySearchHistoryPort, UpdateCitySearchHistoryPort {

  private final CitySearchHistoryRepository citySearchHistoryRepository;

  @Override
  public CitySearchHistory createSearchHistory(CitySearchHistory citySearchHistory) {
    CitySearchHistoryEntity citySearchHistoryEntity = CitySearchHistoryEntity.from(citySearchHistory);
    return citySearchHistoryRepository.save(citySearchHistoryEntity).toDomain();
  }

  @Override
  public void delete(CitySearchHistory searchHistory) {
    CitySearchHistoryEntity citySearchHistoryEntity = getCitySearchHistoryEntity(searchHistory);

    citySearchHistoryEntity.softDelete(citySearchHistoryEntity.getDeletedAt());
  }

  @Override
  public CitySearchHistory updateCount(Long id) {
    CitySearchHistoryEntity citySearchHistoryEntity = citySearchHistoryRepository.findForUpdate(id)
        .orElseThrow(() -> new CityException(ErrorMessage.DELETE_HISTORY_ERROR.getMessage()));
    citySearchHistoryEntity.increaseCount();
    return citySearchHistoryEntity.toDomain();
  }

  private CitySearchHistoryEntity getCitySearchHistoryEntity(CitySearchHistory savedHistory) {
    return citySearchHistoryRepository.findByIdAndDeletedAtIsNull(savedHistory.getId())
        .orElseThrow(() -> new CityException(ErrorMessage.DELETE_HISTORY_ERROR.getMessage()));
  }
}
