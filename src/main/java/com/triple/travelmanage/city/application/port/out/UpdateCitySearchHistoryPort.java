package com.triple.travelmanage.city.application.port.out;

import com.triple.travelmanage.city.domain.CitySearchHistory;

public interface UpdateCitySearchHistoryPort {

  void updateCount(CitySearchHistory savedHistory);

}
