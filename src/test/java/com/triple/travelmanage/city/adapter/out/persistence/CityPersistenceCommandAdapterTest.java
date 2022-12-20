package com.triple.travelmanage.city.adapter.out.persistence;

import static com.triple.travelmanage.common.fixture.city.CityEntityFixture.첫번째_도시_엔티티;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.triple.travelmanage.city.domain.City;
import com.triple.travelmanage.city.exception.CityException;
import com.triple.travelmanage.common.exception.ErrorMessage;
import com.triple.travelmanage.common.repository.BaseRepositoryTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.transaction.annotation.Transactional;

@Import({CityPersistenceCommandAdapter.class})
@EnableJpaAuditing
@Transactional
class CityPersistenceCommandAdapterTest extends BaseRepositoryTest {

  @Autowired
  CityPersistenceCommandAdapter cityPersistenceCommandAdapter;

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

  @DisplayName("도시 등록")
  @Test
  void getCity() {

    //given
    City city = CityEntity.builder()
        .cityName("뉴욕")
        .country("미국")
        .build().toDomain();

    //when
    City saved = cityPersistenceCommandAdapter.create(city);

    //then
    assertEquals(city.getCityName(), saved.getCityName());
  }

  @DisplayName("도시 속성 수정")
  @Test
  void updateCity() {

    //when
    City updateCity = City.builder()
        .id(1L)
        .country("KOREA")
        .cityName("서울")
        .build();

    cityPersistenceCommandAdapter.update(updateCity);

    //then
    CityEntity savedCityEntity = cityRepository.findByIdAndDeletedAtIsNull(1L)
        .orElseThrow(() -> new CityException(ErrorMessage.NOT_EXIST_VALID_CITY.getMessage()));

    assertThat(savedCityEntity.getCountry()).isEqualTo(updateCity.getCountry());
    assertThat(savedCityEntity.getCityName()).isEqualTo(updateCity.getCityName());
  }

  @DisplayName("변경할 도시를 찾지 못하면 예외발생")
  @Test
  void update_exception() {
    //when
    City updateCity = City.builder()
        .country("KOREA")
        .cityName("서울")
        .build();

    assertThatThrownBy(() -> cityPersistenceCommandAdapter.update(updateCity))
        .isInstanceOf(CityException.class);
  }

  @DisplayName("도시 삭제 기능")
  @Test
  void city_delete(){

    City city = City.builder()
        .id(1L)
        .cityName("서울")
        .country("대한민국")
        .build();

    city.delete();

    cityPersistenceCommandAdapter.delete(city);

    CityEntity savedEntity = cityRepository.findById(city.getId()).get();

    assertThat(savedEntity.getDeletedAt()).isNotNull();

  }

  @DisplayName("삭제할 도시가 존재하지 않는 경우 예외 발생")
  @Test
  void delete_exception(){
    City city = City.builder()
        .id(2L)
        .cityName("서울")
        .country("대한민국")
        .build();

    assertThatThrownBy(() -> cityPersistenceCommandAdapter.delete(city))
        .isInstanceOf(CityException.class);
  }

}