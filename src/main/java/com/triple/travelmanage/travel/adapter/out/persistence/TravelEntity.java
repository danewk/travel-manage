package com.triple.travelmanage.travel.adapter.out.persistence;

import com.triple.travelmanage.common.entity.BaseEntity;
import com.triple.travelmanage.travel.domain.Travel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "travels",
    indexes = {
        @Index(name = "idx_travels_created_at", columnList = "created_at"),
        @Index(name = "idx_travels_deleted_at", columnList = "deleted_at"),
        @Index(name = "idx_travels_city_id", columnList = "city_id"),
        @Index(name = "idx_travels_user_id", columnList = "user_id")
    })
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class TravelEntity extends BaseEntity {

  @Column(name = "user_id")
  Long userId;

  @Column(name = "city_id")
  Long cityId;

  @Column(name = "start_date")
  LocalDate startDate;

  @Column(name = "end_date")
  LocalDate endDate;

  @Builder
  public TravelEntity(Long userId, Long cityId, LocalDate startDate, LocalDate endDate) {
    this.userId = userId;
    this.cityId = cityId;
    this.startDate = startDate;
    this.endDate = endDate;
  }

  public static TravelEntity from(Travel travel) {
    return TravelEntity.builder()
        .cityId(travel.getCityId())
        .userId(travel.getUserId())
        .startDate(travel.getStartDate())
        .endDate(travel.getEndDate())
        .build();
  }

  public Travel toDomain(){
    return Travel.builder()
        .id(getId())
        .userId(this.userId)
        .cityId(this.cityId)
        .startDate(this.startDate)
        .endDate(this.endDate)
        .build();
  }

  @Override
  protected void softDelete(LocalDateTime deletedAt) {
    super.softDelete(deletedAt);
  }

  public void update(Travel savedTravel) {
    this.userId = savedTravel.getUserId();
    this.cityId = savedTravel.getCityId();
    this.startDate = savedTravel.getStartDate();
    this.endDate = savedTravel.getEndDate();
  }
}
