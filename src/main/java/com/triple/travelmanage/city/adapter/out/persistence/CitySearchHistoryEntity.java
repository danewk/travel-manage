package com.triple.travelmanage.city.adapter.out.persistence;

import com.triple.travelmanage.city.domain.CitySearchHistory;
import com.triple.travelmanage.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "city_search_history",
    indexes = {
        @Index(name = "idx_city_search_history_created_at", columnList = "created_at"),
        @Index(name = "idx_city_search_history_deleted_at", columnList = "deleted_at")
    },
    uniqueConstraints = {
        @UniqueConstraint(name = "udx_user_id_city_id", columnNames = {"user_id", "city_id", "unique_id"})
    }
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CitySearchHistoryEntity extends BaseEntity {

  @Column(name = "user_id")
  private Long userId;

  @Column(name = "city_id")
  private Long cityId;

  @Column(name = "count")
  private Long count;

  @Column(name = "unique_id")
  private Long uniqueId;

  @Builder
  public CitySearchHistoryEntity(Long userId, Long cityId, Long count, Long uniqueId) {
    this.userId = userId;
    this.cityId = cityId;
    this.count = count;
    this.uniqueId = uniqueId;
  }
  @Override
  protected void softDelete(LocalDateTime deletedAt) {
    super.softDelete(deletedAt);
    this.uniqueId = getId();
  }

  public CitySearchHistory toDomain(){
    return CitySearchHistory.builder()
        .id(getId())
        .userId(userId)
        .cityId(cityId)
        .count(count)
        .createdAt(getCreatedAt())
        .build();
  }

  public static CitySearchHistoryEntity from(CitySearchHistory citySearchHistory){
    return CitySearchHistoryEntity.builder()
        .cityId(citySearchHistory.getCityId())
        .userId(citySearchHistory.getUserId())
        .count(citySearchHistory.getCount())
        .build();
  }

  public void increaseCount(Long count) {
    this.count = count;
  }
}
