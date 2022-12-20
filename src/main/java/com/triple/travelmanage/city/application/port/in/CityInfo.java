package com.triple.travelmanage.city.application.port.in;

import com.triple.travelmanage.city.domain.City;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import lombok.Builder;

@Builder
public record CityInfo(
    Long id,
    String cityName,
    String country,
    LocalDateTime createdAt
) {

  public static CityInfo from(City city) {
    return CityInfo.builder()
        .id(city.getId())
        .cityName(city.getCityName())
        .country(city.getCountry())
        .createdAt(city.getCreatedAt())
        .build();
  }

  @Override
  public boolean equals(final Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    CityInfo other = (CityInfo) obj;
    return Objects.equals(id, other.id);
  }

}
