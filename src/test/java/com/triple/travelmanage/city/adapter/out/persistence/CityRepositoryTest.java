package com.triple.travelmanage.city.adapter.out.persistence;

import static org.assertj.core.api.Assertions.assertThat;

import com.triple.travelmanage.common.repository.BaseRepositoryTest;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class CityRepositoryTest extends BaseRepositoryTest {

  @Autowired
  CityRepository cityRepository;

  @Test
  void load() {
    // given
    CityEntity city = CityEntity.builder()
        .cityName("부산")
        .build();

    //when
    cityRepository.save(city);

    //then
    List<CityEntity> savedCities = cityRepository.findAll();
    assertThat(savedCities).hasSize(1);
  }


}