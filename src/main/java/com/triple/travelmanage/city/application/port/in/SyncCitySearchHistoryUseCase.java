package com.triple.travelmanage.city.application.port.in;

import com.triple.travelmanage.city.adapter.in.web.CityRetrieveRequestDto;

public interface SyncCitySearchHistoryUseCase {

  void syncHistory(Long cityId, CityRetrieveRequestDto requestDto);

}
