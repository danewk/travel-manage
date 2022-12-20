package com.triple.travelmanage.travel.application.port.out;

import com.triple.travelmanage.travel.domain.Travel;

public interface CreateTravelPort {

  Travel create(Travel travel);
}
