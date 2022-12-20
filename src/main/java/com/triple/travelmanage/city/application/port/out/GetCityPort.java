package com.triple.travelmanage.city.application.port.out;

import com.triple.travelmanage.city.application.port.in.command.CityRetrieveCommand;
import com.triple.travelmanage.city.domain.City;

public interface GetCityPort {

  City getCityById(Long cityId);

  City getCityByName(String cityName, String country);

  City getCityForCreate(String cityName, String country);
}
