package com.triple.travelmanage.travel.adapter.out.persistence;

import static com.triple.travelmanage.common.fixture.travel.TravelEntityFixture.사용자1_도시1_여행지_엔티티;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.triple.travelmanage.common.exception.ErrorMessage;
import com.triple.travelmanage.common.repository.BaseRepositoryTest;
import com.triple.travelmanage.travel.domain.Travel;
import com.triple.travelmanage.travel.exception.TravelException;
import java.time.LocalDate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.transaction.annotation.Transactional;

@Import({TravelPersistenceCommandAdapter.class})
@EnableJpaAuditing
@Transactional
class TravelPersistenceCommandAdapterTest extends BaseRepositoryTest {

  @Autowired
  TravelPersistenceCommandAdapter travelPersistenceCommandAdapter;

  @Autowired
  TravelRepository travelRepository;

  @BeforeEach
  void setUp() {
    travelRepository.saveAndFlush(사용자1_도시1_여행지_엔티티());
  }

  @AfterEach
  void clean() {
    travelRepository.deleteAll();
  }

  @DisplayName("여행지 등록 기능")
  @Test
  void create_travel(){
    //given
    Travel travel = TravelEntity.builder()
        .userId(1L)
        .cityId(1L)
        .startDate(LocalDate.of(2022, 12, 20))
        .endDate(LocalDate.of(2022, 12, 22))
        .build().toDomain();

    //when
    Travel saved = travelPersistenceCommandAdapter.create(travel);

    //then
    assertEquals(travel.getStartDate(), saved.getStartDate());
    assertEquals(travel.getEndDate(), saved.getEndDate());
    assertEquals(travel.getUserId(), saved.getUserId());
    assertThat(saved.getId()).isNotNull();
  }

  @DisplayName("여행 삭제 기능")
  @Test
  void travel_delete(){

    Travel travel = Travel.builder()
        .id(1L)
        .cityId(1L)
        .userId(1L)
        .startDate(LocalDate.of(2022, 12, 20))
        .endDate(LocalDate.of(2022, 12, 22))
        .build();

    travel.delete();

    travelPersistenceCommandAdapter.delete(travel);

    TravelEntity travelEntity = travelRepository.findById(travel.getId()).get();

    assertThat(travelEntity.getDeletedAt()).isNotNull();

  }

  @DisplayName("삭제할 여행이 존재하지 않는 경우 예외 발생")
  @Test
  void delete_exception(){
    Travel travel = Travel.builder()
        .id(3L)
        .cityId(1L)
        .userId(1L)
        .startDate(LocalDate.of(2022, 12, 20))
        .endDate(LocalDate.of(2022, 12, 22))
        .build();

    assertThatThrownBy(() -> travelPersistenceCommandAdapter.delete(travel))
        .isInstanceOf(TravelException.class);
  }

  @DisplayName("여행 속성 수정")
  @Test
  void update_travel(){
    //when
    Travel updatedTravel = Travel.builder()
        .id(2L)
        .cityId(2L) //도시를 변경함
        .userId(1L)
        .startDate(LocalDate.of(2022, 12, 20))
        .endDate(LocalDate.of(2022, 12, 22))
        .build();

    travelPersistenceCommandAdapter.update(updatedTravel);

    //then
    TravelEntity savedEntity = travelRepository.findByIdAndDeletedAtIsNull(1L)
        .orElseThrow(() -> new TravelException(ErrorMessage.NOT_EXIST_TRAVEL.getMessage()));

    assertThat(savedEntity.cityId).isEqualTo(updatedTravel.getCityId());

  }

  @DisplayName("변경할 여행을 찾지 못하면 예외발생")
  @Test
  void update_exception(){
    //when
    Travel updatedTravel = Travel.builder()
        .id(2L)
        .cityId(2L) //도시를 변경함
        .userId(1L)
        .startDate(LocalDate.of(2022, 12, 20))
        .endDate(LocalDate.of(2022, 12, 22))
        .build();


    assertThatThrownBy(() -> travelPersistenceCommandAdapter.update(updatedTravel))
        .isInstanceOf(TravelException.class);
  }

}