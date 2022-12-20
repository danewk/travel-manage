package com.triple.travelmanage.travel.application.port.in;

import com.triple.travelmanage.travel.application.port.in.command.TravelCreateCommand;

public interface CreateTravelUseCase {

  TravelInfo createTravel(TravelCreateCommand command);
}
