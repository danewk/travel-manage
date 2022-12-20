package com.triple.travelmanage.city.application.port.in;

import com.triple.travelmanage.city.application.port.in.command.CityRetrieveCommand;

public interface GetTravelCityUseCase {

  CityInfo getCityInfoForTravel(CityRetrieveCommand of);

  CityInfo getCity(Long cityId);
}
