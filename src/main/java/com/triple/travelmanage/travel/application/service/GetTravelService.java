package com.triple.travelmanage.travel.application.service;

import com.triple.travelmanage.city.application.port.in.CityInfo;
import com.triple.travelmanage.common.annotation.UseCase;
import com.triple.travelmanage.travel.application.port.in.GetTravelUseCase;
import com.triple.travelmanage.travel.application.port.in.TravelInfo;
import com.triple.travelmanage.travel.application.port.out.GetTravelCityInfoPort;
import com.triple.travelmanage.travel.application.port.out.GetTravelPort;
import com.triple.travelmanage.travel.domain.Travel;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class GetTravelService implements GetTravelUseCase {

  private final GetTravelPort getTravelPort;
  private final GetTravelCityInfoPort getTravelCityInfoPort;

  @Override
  public TravelInfo getTravel(Long travelId) {
    Travel travel = getTravelPort.getTravelById(travelId);
    CityInfo cityInfo = getTravelCityInfoPort.getCityById(travel.getCityId());

    return TravelInfo.from(getTravelPort.getTravelById(travelId), cityInfo);
  }

  @Override
  public List<TravelInfo> getTravelsByCityId(Long cityId) {
    CityInfo cityInfo = getTravelCityInfoPort.getCityById(cityId);
    return getTravelPort.getTravelsByCityId(cityId).stream()
        .map(travel -> TravelInfo.from(travel, cityInfo)).toList();
  }

  @Override
  public List<TravelInfo> getTravelsByUserId(Long userId) {
    List<TravelInfo> result = new ArrayList<>();
    List<Travel> travels = getTravelPort.getTravelsByUserId(userId);
    for (Travel travel : travels) {
      CityInfo cityInfo = getTravelCityInfoPort.getCityById(travel.getCityId());
      result.add(TravelInfo.from(travel, cityInfo));
    }
    return result;
  }
}
