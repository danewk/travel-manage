package com.triple.travelmanage.city.application.port.in;

import com.triple.travelmanage.city.application.port.in.command.CityUpdateCommand;

public interface UpdateCityUseCase {

  CityInfo updateCity(CityUpdateCommand command);
}
