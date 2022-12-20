package com.triple.travelmanage.city.application.port.out;

import com.triple.travelmanage.city.domain.City;

public interface UpdateCityPort {

  void update(City command);
}
