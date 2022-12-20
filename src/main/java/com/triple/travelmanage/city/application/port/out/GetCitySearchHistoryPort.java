package com.triple.travelmanage.city.application.port.out;

import com.triple.travelmanage.city.domain.CitySearchHistory;
import com.triple.travelmanage.travel.application.port.in.TravelInfo;
import java.util.List;

public interface GetCitySearchHistoryPort {

  CitySearchHistory getSearchHistory(Long cityId, Long userId);

  List<CitySearchHistory> getSearchHistoryByUserIdAndCityIds(Long userId, List<TravelInfo> travelInfos);
}
