package com.triple.travelmanage.travel.application.port.out;

import com.triple.travelmanage.travel.domain.Travel;

public interface DeleteTravelPort {

  void delete(Travel travel);
}
