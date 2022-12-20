package com.triple.travelmanage.travel.domain;

import com.triple.travelmanage.city.application.port.in.CityInfo;
import com.triple.travelmanage.travel.application.port.in.command.TravelUpdateCommand;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import org.springframework.util.Assert;

@Getter
public class Travel {
  private final Long id;
  private Long cityId;
  private Long userId;
  private LocalDate startDate;
  private LocalDate endDate;

  private LocalDateTime deletedAt;

  @Builder
  public Travel(Long id, Long cityId, Long userId, LocalDate startDate, LocalDate endDate, LocalDateTime deletedAt) {
    if(!startDate.isBefore(endDate)){
      Assert.state(startDate.isBefore(endDate),"출발일은 시작일보다 항상 앞이여야 합니다.");
    }
    Assert.notNull(startDate, "startDate must not be null");
    Assert.notNull(endDate, "endDate must not be null");
    this.userId = userId;
    this.id = id;
    this.cityId = cityId;
    this.startDate = startDate;
    this.endDate = endDate;
    this.deletedAt = deletedAt;
  }

  public void delete() {
    this.deletedAt = LocalDateTime.now();
  }

  public void update(TravelUpdateCommand command, CityInfo cityInfo) {
    if(!command.startDate().isBefore(command.endDate())){
      Assert.state(command.startDate().isBefore(command.endDate()),"출발일은 시작일보다 항상 앞이여야 합니다.");
    }
    this.cityId = cityInfo.id();
    this.userId = command.userId();
    this.startDate = command.startDate();
    this.endDate = command.endDate();
  }
}
