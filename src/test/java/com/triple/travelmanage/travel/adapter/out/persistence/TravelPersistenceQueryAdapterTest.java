package com.triple.travelmanage.travel.adapter.out.persistence;

import static com.triple.travelmanage.common.fixture.city.CityEntityFixture.첫번째_도시;
import static com.triple.travelmanage.common.fixture.travel.TravelEntityFixture.첫번째여행지;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.triple.travelmanage.city.domain.City;
import com.triple.travelmanage.city.exception.CityException;
import com.triple.travelmanage.common.repository.BaseRepositoryTest;
import com.triple.travelmanage.travel.domain.Travel;
import com.triple.travelmanage.travel.exception.TravelException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.transaction.annotation.Transactional;

@Import({TravelPersistenceQueryAdapter.class})
@EnableJpaAuditing
@Transactional
class TravelPersistenceQueryAdapterTest extends BaseRepositoryTest {

  @Autowired
  TravelPersistenceQueryAdapter travelPersistenceQueryAdapter;

  @Autowired
  TravelRepository travelRepository;

  @BeforeEach
  void setUp() {
    travelRepository.saveAndFlush(첫번째여행지());
  }

  @AfterEach
  void clean() {
    travelRepository.deleteAll();
  }

  @DisplayName("단일 여행지 조회 기능")
  @Test
  void getCity(){
    Travel travel = travelPersistenceQueryAdapter.getTravelById(1L);
    assertEquals(첫번째여행지().getCityId(), travel.getCityId());
  }

  @DisplayName("단일 도시 조회 실패시 예외 발생")
  @Test
  void getCity_exception(){
    assertThatThrownBy(() -> travelPersistenceQueryAdapter.getTravelById(2L))
        .isInstanceOf(TravelException.class);
  }


}