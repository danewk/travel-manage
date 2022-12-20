package com.triple.travelmanage.travel.application.service;

import com.triple.travelmanage.city.application.port.in.CityInfo;
import com.triple.travelmanage.common.annotation.UseCase;
import com.triple.travelmanage.travel.application.port.in.CreateTravelUseCase;
import com.triple.travelmanage.travel.application.port.in.TravelInfo;
import com.triple.travelmanage.travel.application.port.in.command.TravelCreateCommand;
import com.triple.travelmanage.travel.application.port.out.CreateTravelPort;
import com.triple.travelmanage.travel.application.port.out.GetTravelCityInfoPort;
import com.triple.travelmanage.travel.domain.Travel;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class CreateTravelService implements CreateTravelUseCase {

  private final CreateTravelPort createTravelPort;
  private final GetTravelCityInfoPort getTravelCityInfoPort;
  @Override
  public TravelInfo createTravel(TravelCreateCommand command) {
    CityInfo city = getTravelCityInfoPort.getCityInfo(command.cityName(), command.country());

    Travel travel = command.toDomain(city.id());

    return TravelInfo.from(createTravelPort.create(travel), city);
  }
}
