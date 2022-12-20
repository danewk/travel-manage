package com.triple.travelmanage.travel.application.port.out;

import com.triple.travelmanage.city.application.port.in.CityInfo;

public interface GetTravelCityInfoPort {

  CityInfo getCityInfo(String cityName, String country);
  CityInfo getCityById(Long cityId);
}
