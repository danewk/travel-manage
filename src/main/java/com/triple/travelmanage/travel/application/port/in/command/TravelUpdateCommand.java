package com.triple.travelmanage.travel.application.port.in.command;

import com.triple.travelmanage.travel.adapter.in.web.TravelUpdateRequestDto;
import com.triple.travelmanage.travel.domain.Travel;
import jakarta.validation.Valid;
import java.time.LocalDate;
import lombok.Builder;

@Builder
public record TravelUpdateCommand(
    Long userId,
    Long travelId,
    String country,
    String cityName,
    LocalDate startDate,
    LocalDate endDate
) {

  public static TravelUpdateCommand of(TravelUpdateRequestDto requestDto, Long travelId) {
    return TravelUpdateCommand.builder()
        .userId(requestDto.userId())
        .travelId(travelId)
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
