package com.triple.travelmanage.city.adapter.out.persistence;

import static com.triple.travelmanage.common.fixture.city.CitySearchHistoryEntityFixture.유저1_도시1_조회_내역_엔티티;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.triple.travelmanage.city.domain.CitySearchHistory;
import com.triple.travelmanage.common.repository.BaseRepositoryTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.transaction.annotation.Transactional;

@Import({CitySearchHistoryPersistenceQueryAdapter.class})
@EnableJpaAuditing
@Transactional
class CitySearchHistoryPersistenceQueryAdapterTest extends BaseRepositoryTest {

  @Autowired
  CitySearchHistoryPersistenceQueryAdapter citySearchHistoryPersistenceQueryAdapter;

  @Autowired
  CitySearchHistoryRepository citySearchHistoryRepository;

  @BeforeEach
  void setUp(){
    citySearchHistoryRepository.saveAndFlush(유저1_도시1_조회_내역_엔티티());
  }

  @AfterEach
  void clean() {
    citySearchHistoryRepository.deleteAll();
  }

  @DisplayName("유저1의 도시1 조회 내역 카운트")
  @Test
  void getCity(){
    CitySearchHistory searchHistory = citySearchHistoryPersistenceQueryAdapter.getSearchHistory(1L, 1L);
    assertEquals(유저1_도시1_조회_내역_엔티티().getCount(), searchHistory.getCount());
  }


}