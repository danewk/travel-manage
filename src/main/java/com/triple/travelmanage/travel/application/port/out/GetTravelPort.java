package com.triple.travelmanage.travel.application.port.out;

import com.triple.travelmanage.travel.domain.Travel;
import java.util.List;

public interface GetTravelPort {

  Travel getTravelById(Long travelId);

  List<Travel> getTravelsByCityId(Long cityId);

  List<Travel> getTravelsByUserId(Long userId);
}
