package com.triple.travelmanage.city.application.service;

import com.triple.travelmanage.city.application.port.in.CityInfo;
import com.triple.travelmanage.city.application.port.in.GetTravelCityUseCase;
import com.triple.travelmanage.city.application.port.in.command.CityRetrieveCommand;
import com.triple.travelmanage.city.application.port.out.GetCityPort;
import com.triple.travelmanage.common.annotation.UseCase;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class GetTravelCityService implements GetTravelCityUseCase {

  private final GetCityPort getCityPort;
  @Override
  public CityInfo getCityInfoForTravel(CityRetrieveCommand command) {
    return CityInfo.from(getCityPort.getCityByName(command.cityName(), command.country()));
  }

  @Override
  public CityInfo getCity(final Long cityId) {
    return CityInfo.from(getCityPort.getCityById(cityId));
  }

}
