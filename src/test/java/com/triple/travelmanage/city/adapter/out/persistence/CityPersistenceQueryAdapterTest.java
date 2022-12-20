package com.triple.travelmanage.city.adapter.out.persistence;

import static com.triple.travelmanage.common.fixture.city.CityEntityFixture.첫번째_도시_엔티티;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.triple.travelmanage.city.domain.City;
import com.triple.travelmanage.city.exception.CityException;
import com.triple.travelmanage.common.repository.BaseRepositoryTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.transaction.annotation.Transactional;

@Import({CityPersistenceQueryAdapter.class})
@EnableJpaAuditing
@Transactional
class CityPersistenceQueryAdapterTest extends BaseRepositoryTest {

  @Autowired
  CityPersistenceQueryAdapter cityPersistenceQueryAdapter;

  @Autowired
  CityRepository cityRepository;

  @BeforeEach
  void setUp() {
    cityRepository.saveAndFlush(첫번째_도시_엔티티());
  }

  @AfterEach
  void clean() {
    cityRepository.deleteAll();
  }

  @DisplayName("단일 도시 조회 API")
  @Test
  void getCity(){
    City savedByCity = cityPersistenceQueryAdapter.getCityById(1L);
    assertEquals(첫번째_도시_엔티티().getCityName(), savedByCity.getCityName());
  }

  @DisplayName("단일 도시 조회 API - 실패시 예외 발생")
  @Test
  void getCity_exception(){
    assertThatThrownBy(() -> cityPersistenceQueryAdapter.getCityById(2L))
        .isInstanceOf(CityException.class);
  }


}