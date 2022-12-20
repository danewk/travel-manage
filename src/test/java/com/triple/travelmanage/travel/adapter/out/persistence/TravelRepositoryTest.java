package com.triple.travelmanage.travel.adapter.out.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.triple.travelmanage.common.repository.BaseRepositoryTest;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class TravelRepositoryTest extends BaseRepositoryTest {

  @Autowired
  TravelRepository travelRepository;

  @Test
  void load(){
    //given
    TravelEntity travelEntity = TravelEntity.builder()
        .userId(1L)
        .cityId(1L)
        .startDate(LocalDate.of(2022, 12, 20))
        .endDate(LocalDate.of(2022, 12, 22))
        .build();

    //when
    travelRepository.save(travelEntity);

    //then
    List<TravelEntity> savedTravels = travelRepository.findAll();
    assertEquals(1, savedTravels.size());
  }

}