package com.triple.travelmanage.travel.application.port.in;

import com.triple.travelmanage.travel.application.port.in.command.TravelUpdateCommand;

public interface UpdateTravelUseCase {

  TravelInfo updateTravel(TravelUpdateCommand of);
}
