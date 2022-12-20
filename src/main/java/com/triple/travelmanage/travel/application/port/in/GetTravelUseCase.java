package com.triple.travelmanage.travel.application.port.in;

import java.util.List;

public interface GetTravelUseCase {

  TravelInfo getTravel(Long travelId);

  List<TravelInfo> getTravelsByCityId(Long cityId);

  List<TravelInfo> getTravelsByUserId(Long userId);
}
