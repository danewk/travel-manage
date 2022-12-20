package com.triple.travelmanage.travel.application.port.in.command;

import com.triple.travelmanage.travel.adapter.in.web.TravelCreateRequestDto;
import com.triple.travelmanage.travel.domain.Travel;
import java.time.LocalDate;
import lombok.Builder;
import org.springframework.util.Assert;

@Builder
public record TravelCreateCommand(
    Long userId,
    String country,
    String cityName,
    LocalDate startDate,
    LocalDate endDate
) {

  public static TravelCreateCommand of(TravelCreateRequestDto requestDto) {
    return TravelCreateCommand.builder()
        .userId(requestDto.userId())
        .country(requestDto.country())
        .cityName(requestDto.cityName())
        .startDate(requestDto.startDate())
        .endDate(requestDto.endDate())
        .build();
  }

  public Travel toDomain(Long cityId){
    return Travel.builder()
        .cityId(cityId)
        .userId(userId)
        .startDate(startDate)
        .endDate(endDate)
        .build();
  }
}
