package com.triple.travelmanage.city.adapter.out.persistence;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.triple.travelmanage.common.repository.BaseRepositoryTest;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

class CitySearchHistoryRepositoryTest extends BaseRepositoryTest {

  @Autowired
  CitySearchHistoryRepository citySearchHistoryRepository;

  @Test
  void load(){
    //given
    CitySearchHistoryEntity citySearchHistoryEntity = CitySearchHistoryEntity.builder()
        .count(1L)
        .cityId(1L)
        .userId(1L)
        .build();

    //when
    citySearchHistoryRepository.save(citySearchHistoryEntity);

    //then
    List<CitySearchHistoryEntity> saved = citySearchHistoryRepository.findAll();

    assertThat(saved).hasSize(1);

  }

  @DisplayName("제약 조건으로 인한 exception")
  @Test
  void save_exception(){
    //given
    CitySearchHistoryEntity citySearchHistoryEntity = CitySearchHistoryEntity.builder()
        .count(1L)
        .cityId(1L)
        .userId(1L)
        .uniqueId(1L)
        .build();

    CitySearchHistoryEntity copied = CitySearchHistoryEntity.builder()
        .count(1L)
        .cityId(1L)
        .userId(1L)
        .uniqueId(1L)
        .build();


    //when
    citySearchHistoryRepository.saveAndFlush(citySearchHistoryEntity);

    assertThatThrownBy(() -> citySearchHistoryRepository.saveAndFlush(copied))
        .isInstanceOf(DataIntegrityViolationException.class);

  }

}