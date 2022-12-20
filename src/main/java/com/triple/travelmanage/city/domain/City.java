package com.triple.travelmanage.city.domain;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import org.springframework.util.Assert;

@Getter
public class City {

  private final Long id;
  private String cityName;
  private String country;
  private final LocalDateTime createdAt;

  private LocalDateTime deletedAt;

  @Builder
  public City(Long id, String cityName, String country, LocalDateTime createdAt, LocalDateTime deletedAt) {
    Assert.notNull(cityName, "cityName must not be null");
    Assert.notNull(country, "country must not be null");
    this.id = id;
    this.cityName = cityName;
    this.country = country;
    this.createdAt = createdAt;
    this.deletedAt = deletedAt;
  }

  public void update(City city) {
    this.cityName = city.getCityName();
    this.country = city.getCountry();
  }

  public void delete() {
    this.deletedAt = LocalDateTime.now();
  }
}
