package com.triple.travelmanage.travel.application.port.in;

import com.triple.travelmanage.city.application.port.in.CityInfo;
import com.triple.travelmanage.travel.domain.Travel;
import java.time.LocalDate;
import lombok.Builder;

@Builder
public record TravelInfo(
    Long id,
    CityInfo cityInfo,
    Long userId,
    LocalDate startDate,
    LocalDate endDate
) {

  public static TravelInfo from(Travel travel, CityInfo cityInfo){
    return TravelInfo.builder()
        .id(travel.getId())
        .cityInfo(cityInfo)
        .userId(travel.getUserId())
        .startDate(travel.getStartDate())
        .endDate(travel.getEndDate())
        .build();
  }

}
