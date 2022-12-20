package com.triple.travelmanage.city.application.port.out;

import com.triple.travelmanage.travel.application.port.in.TravelInfo;
import java.util.List;

public interface GetTravelPort {


  List<TravelInfo> findAllByCityId(Long cityId);

  List<TravelInfo> findAllByUserId(Long userId);
}
