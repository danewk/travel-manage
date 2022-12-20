package com.triple.travelmanage.travel.application.service;

import com.triple.travelmanage.common.annotation.UseCase;
import com.triple.travelmanage.travel.application.port.in.DeleteTravelUseCase;
import com.triple.travelmanage.travel.application.port.out.DeleteTravelPort;
import com.triple.travelmanage.travel.application.port.out.GetTravelPort;
import com.triple.travelmanage.travel.domain.Travel;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class DeleteTravelService implements DeleteTravelUseCase {

  private final DeleteTravelPort deleteTravelPort;

  private final GetTravelPort getTravelPort;

  @Override
  public void deleteTravel(Long travelId) {
    Travel savedTravel = getTravelPort.getTravelById(travelId);
    savedTravel.delete();
    deleteTravelPort.delete(savedTravel);
  }
}
