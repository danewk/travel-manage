package com.triple.travelmanage.city.adapter.out.persistence;

import com.triple.travelmanage.city.domain.City;
import com.triple.travelmanage.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cities",
    indexes = {
        @Index(name = "idx_cities_created_at", columnList = "created_at"),
        @Index(name = "idx_cities_deleted_at", columnList = "deleted_at"),
        @Index(name = "idx_cities_city_name", columnList = "city_name"),
        @Index(name = "idx_cities_country", columnList = "country")
    })
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CityEntity extends BaseEntity {

  @Column(name = "city_name")
  private String cityName;

  @Column(name = "country")
  private String country;

  @Builder
  public CityEntity(String cityName, String country) {
    this.cityName = cityName;
    this.country = country;
  }

  public static CityEntity from(City city) {
    return CityEntity.builder()
        .cityName(city.getCityName())
        .country(city.getCountry())
        .build();
  }

  public City toDomain() {
    return City.builder()
        .id(getId())
        .cityName(cityName)
        .country(country)
        .createdAt(getCreatedAt())
        .deletedAt(getDeletedAt())
        .build();
  }

  public void update(City city) {
    this.cityName = city.getCityName();
    this.country = city.getCountry();
  }

  @Override
  protected void softDelete(LocalDateTime deletedAt) {
    super.softDelete(deletedAt);
  }
}
