package com.triple.travelmanage.city.adapter.out.travel;

import com.triple.travelmanage.city.application.port.out.GetTravelPort;
import com.triple.travelmanage.common.annotation.PersistenceAdapter;
import com.triple.travelmanage.travel.application.port.in.GetTravelUseCase;
import com.triple.travelmanage.travel.application.port.in.TravelInfo;
import java.util.List;
import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
public class CityTravelPersistenceQueryAdapter implements GetTravelPort {

  private final GetTravelUseCase getTravelUseCase;

  @Override
  public List<TravelInfo> findAllByCityId(Long cityId) {
    return getTravelUseCase.getTravelsByCityId(cityId);
  }

  @Override
  public List<TravelInfo> findAllByUserId(Long userId) {
    return getTravelUseCase.getTravelsByUserId(userId);
  }
}
