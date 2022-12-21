package com.triple.travelmanage.city.domain;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CitySearchHistory {

  private final Long id;
  private final Long cityId;
  private final Long userId;
  private Long count;
  private final LocalDateTime createdAt;
  private LocalDateTime deletedAt;

  @Builder
  public CitySearchHistory(Long id, Long cityId, Long userId, Long count, LocalDateTime createdAt) {
    this.id = id;
    this.cityId = cityId;
    this.userId = userId;
    this.count = count;
    this.createdAt = createdAt;
  }

  public void delete() {
    this.deletedAt = LocalDateTime.now();
  }

  public Long increaseCount() {
    this.count = count + 1;
    return count;
  }
}
