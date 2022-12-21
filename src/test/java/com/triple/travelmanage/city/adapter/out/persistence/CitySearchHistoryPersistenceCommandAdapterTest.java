package com.triple.travelmanage.city.adapter.out.persistence;

import static com.triple.travelmanage.common.fixture.city.CitySearchHistoryDomainFixture.유저1_도시1_조회_내역_도메인;
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

@Import({CitySearchHistoryPersistenceCommandAdapter.class})
@EnableJpaAuditing
@Transactional
class CitySearchHistoryPersistenceCommandAdapterTest extends BaseRepositoryTest {

  @Autowired
  CitySearchHistoryPersistenceCommandAdapter citySearchHistoryPersistenceCommandAdapter;
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


  @DisplayName("조회수 업데이트 테스트")
  @Test
  void updateCount(){
    //given
    CitySearchHistory citySearchHistory = 유저1_도시1_조회_내역_도메인();

    //when
    CitySearchHistory saved = citySearchHistoryPersistenceCommandAdapter.updateCount(citySearchHistory.getId());

    assertEquals(11,saved.getCount());
  }



}