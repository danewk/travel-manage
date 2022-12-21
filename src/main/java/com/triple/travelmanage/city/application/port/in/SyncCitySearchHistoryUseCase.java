package com.triple.travelmanage.city.application.port.in;

import com.triple.travelmanage.city.application.port.in.command.SyncHistoryCommand;
import com.triple.travelmanage.city.domain.CitySearchHistory;

public interface SyncCitySearchHistoryUseCase {

  CitySearchHistory syncHistory(SyncHistoryCommand command);

}
