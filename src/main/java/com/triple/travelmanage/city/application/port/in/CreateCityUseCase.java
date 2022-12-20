package com.triple.travelmanage.city.application.port.in;

import com.triple.travelmanage.city.application.port.in.command.CityCreateCommand;

public interface CreateCityUseCase {

  CityInfo createCity(CityCreateCommand command);

}
