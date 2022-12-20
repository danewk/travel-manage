package com.triple.travelmanage.travel.application.service;

import com.triple.travelmanage.city.application.port.in.CityInfo;
import com.triple.travelmanage.common.annotation.UseCase;
import com.triple.travelmanage.travel.application.port.in.TravelInfo;
import com.triple.travelmanage.travel.application.port.in.UpdateTravelUseCase;
import com.triple.travelmanage.travel.application.port.in.command.TravelUpdateCommand;
import com.triple.travelmanage.travel.application.port.out.GetTravelCityInfoPort;
import com.triple.travelmanage.travel.application.port.out.GetTravelPort;
import com.triple.travelmanage.travel.application.port.out.UpdateTravelPort;
import com.triple.travelmanage.travel.domain.Travel;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class UpdateTravelService implements UpdateTravelUseCase {

  private final UpdateTravelPort updateTravelPort;
  private final GetTravelPort getTravelPort;
  private final GetTravelCityInfoPort getTravelCityInfoPort;
  @Override
  public TravelInfo updateTravel(TravelUpdateCommand command) {
    Travel savedTravel = getTravelPort.getTravelById(command.travelId());
    CityInfo cityInfo = getTravelCityInfoPort.getCityInfo(command.cityName(), command.country());

    savedTravel.update(command, cityInfo);
    updateTravelPort.update(savedTravel);
    return TravelInfo.from(savedTravel, cityInfo);
  }
}
