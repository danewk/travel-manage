package com.triple.travelmanage.travel.adapter.out.city;

import com.triple.travelmanage.city.application.port.in.CityInfo;
import com.triple.travelmanage.city.application.port.in.GetCityUseCase;
import com.triple.travelmanage.city.application.port.in.GetTravelCityUseCase;
import com.triple.travelmanage.city.application.port.in.command.CityRetrieveCommand;
import com.triple.travelmanage.common.annotation.PersistenceAdapter;
import com.triple.travelmanage.travel.application.port.out.GetTravelCityInfoPort;
import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
public class TravelCityPersistenceQueryAdapter implements GetTravelCityInfoPort {
  private final GetTravelCityUseCase getTravelCityUseCase;

  @Override
  public CityInfo getCityInfo(String cityName, String country) {
    return getTravelCityUseCase.getCityInfoForTravel(CityRetrieveCommand.of(cityName, country));
  }

  @Override
  public CityInfo getCityById(Long cityId) {
    return getTravelCityUseCase.getCity(cityId);
  }

}
