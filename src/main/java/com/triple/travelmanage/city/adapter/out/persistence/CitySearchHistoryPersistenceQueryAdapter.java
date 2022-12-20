package com.triple.travelmanage.city.adapter.out.persistence;

import com.triple.travelmanage.city.application.port.in.CityInfo;
import com.triple.travelmanage.city.application.port.out.GetCitySearchHistoryPort;
import com.triple.travelmanage.city.domain.CitySearchHistory;
import com.triple.travelmanage.common.annotation.PersistenceAdapter;
import com.triple.travelmanage.travel.application.port.in.TravelInfo;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@PersistenceAdapter
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CitySearchHistoryPersistenceQueryAdapter implements GetCitySearchHistoryPort {

  private final CitySearchHistoryRepository citySearchHistoryRepository;

  @Override
  public CitySearchHistory getSearchHistory(Long cityId, Long userId) {
    return citySearchHistoryRepository.findByCityIdAndUserIdAndDeletedAtIsNull(cityId, userId)
        .orElseGet(() -> CitySearchHistoryEntity.builder().build()).toDomain();
  }

  @Override
  public List<CitySearchHistory> getSearchHistoryByUserIdAndCityIds(Long userId, List<TravelInfo> travelInfos) {
    List<Long> cityIds = travelInfos.stream()
        .map(TravelInfo::cityInfo)
        .map(CityInfo::id).toList();

    return citySearchHistoryRepository.findAllByUserIdAndCityIdInAndDeletedAtIsNull(userId, cityIds)
        .stream().map(CitySearchHistoryEntity::toDomain).toList();
  }
}
