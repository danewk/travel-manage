package com.triple.travelmanage.city.adapter.out.persistence;

import static com.triple.travelmanage.common.fixture.city.CitySearchHistoryEntityFixture.유저1_도시1_조회수1_엔티티;
import static org.assertj.core.api.Assertions.assertThat;

import com.triple.travelmanage.city.domain.CitySearchHistory;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;

@SpringBootTest
class CitySearchHistoryAdapterTest{

  @Autowired
  CitySearchHistoryPersistenceCommandAdapter citySearchHistoryPersistenceCommandAdapter;

  @Autowired
  CitySearchHistoryPersistenceQueryAdapter citySearchHistoryPersistenceQueryAdapter;


  @Autowired
  CitySearchHistoryRepository citySearchHistoryRepository;

  @BeforeEach
  void setUp(){
    citySearchHistoryRepository.saveAndFlush(유저1_도시1_조회수1_엔티티());
  }

  @AfterEach
  void clean() {
    citySearchHistoryRepository.deleteAll();
  }


  @Test
  @DisplayName("조회수 업데이트 (멀티 스레드) 테스트")
  void updateCountForMultiThreadTest() throws InterruptedException {


    AtomicInteger successCount = new AtomicInteger();
    int numberOfExcute = 100;
    ExecutorService service = Executors.newFixedThreadPool(10);
    CountDownLatch latch = new CountDownLatch(numberOfExcute);

    for (int i = 0; i < numberOfExcute; i++) {
      service.execute(() -> {
        try {
          citySearchHistoryPersistenceCommandAdapter.updateCount(1L);
          successCount.getAndIncrement();
          System.out.println("성공");
        } catch (ObjectOptimisticLockingFailureException oe) {
          System.out.println("충돌감지");
        } catch (Exception e) {
          System.out.println(e.getMessage());
        }
        latch.countDown();
      });
    }
    latch.await();

    CitySearchHistory searchHistory = citySearchHistoryPersistenceQueryAdapter.getSearchHistory(1L, 1L);

    assertThat(searchHistory.getCount()).isEqualTo(101L);
    assertThat(successCount.get()).isEqualTo(100);
  }
}