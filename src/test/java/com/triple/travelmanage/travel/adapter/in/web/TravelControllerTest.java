package com.triple.travelmanage.travel.adapter.in.web;

import static com.triple.travelmanage.common.fixture.city.CityEntityFixture.경주_도시_엔티티;
import static com.triple.travelmanage.common.fixture.city.CityEntityFixture.부산_도시_엔티티;
import static com.triple.travelmanage.common.fixture.city.CityEntityFixture.서울_도시_엔티티;
import static com.triple.travelmanage.common.fixture.city.CityEntityFixture.제주도_도시_엔티티;
import static com.triple.travelmanage.common.fixture.travel.TravelEntityFixture.사용자1_도시1_여행지_엔티티;
import static com.triple.travelmanage.common.fixture.travel.TravelEntityFixture.사용자1_도시2_여행지_엔티티;
import static org.assertj.core.api.Assertions.assertThat;

import com.triple.travelmanage.city.adapter.out.persistence.CityRepository;
import com.triple.travelmanage.common.BaseIntegrationTest;
import com.triple.travelmanage.travel.adapter.out.persistence.TravelRepository;
import com.triple.travelmanage.travel.application.port.in.TravelInfo;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

class TravelControllerTest extends BaseIntegrationTest {

  private final String baseUrl = "/api/v1";

  @Autowired
  TravelRepository travelRepository;

  @Autowired
  CityRepository cityRepository;

  @BeforeEach
  void init() {
    travelRepository.saveAll(
        List.of(
            사용자1_도시1_여행지_엔티티(),
            사용자1_도시2_여행지_엔티티()
        )
    );
    cityRepository.saveAll(
        List.of(
            서울_도시_엔티티(),
            경주_도시_엔티티(),
            부산_도시_엔티티(),
            제주도_도시_엔티티()
        )
    );
  }

  @AfterEach
  void clean() {
    travelRepository.deleteAll();
  }

  @DisplayName("여행 단건 조회")
  @Test
  void getTravel(){

    ExtractableResponse<Response> response = RestAssured
        .given()
        .log().all()
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .when()
        .get(baseUrl + "/travel/1")
        .then()
        .log().all()
        .extract();

    TravelInfo travelInfo = response.jsonPath().getObject("data", TravelInfo.class);

    assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    assertThat(travelInfo.cityInfo().cityName()).isEqualTo("서울");
  }

  @DisplayName("여행 단건 조회 오류")
  @Test
  void getTravelException(){

    ExtractableResponse<Response> response = RestAssured
        .given()
        .log().all()
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .when()
        .get(baseUrl + "/travel/11")
        .then()
        .log().all()
        .extract();

    assertThat(response.statusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());

  }

  @DisplayName("여행 등록")
  @Test
  void createCity(){
    Map<String, Object> requestDto = new HashMap<>();
    requestDto.put("cityName", "부산");
    requestDto.put("country", "대한민국");
    requestDto.put("userId", 1L);
    requestDto.put("startDate", LocalDate.of(2022,12,25));
    requestDto.put("endDate", LocalDate.of(2022,12,30));

    ExtractableResponse<Response> response = RestAssured
        .given()
        .log().all()
        .body(requestDto)
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .when()
        .post(baseUrl + "/travel")
        .then()
        .log().all()
        .extract();

    TravelInfo responseInfo = response.jsonPath().getObject("data", TravelInfo.class);

    assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    assertThat(responseInfo).isNotNull();
    assertThat(responseInfo.cityInfo().cityName()).isEqualTo("부산");

  }

  @DisplayName("등록되지 않은 도시를 여행에 등록시 오류 발생")
  @Test
  void createCityException(){
    Map<String, Object> requestDto = new HashMap<>();
    requestDto.put("cityName", "포항");
    requestDto.put("country", "대한민국");
    requestDto.put("userId", 1L);
    requestDto.put("startDate", LocalDate.of(2022,12,25));
    requestDto.put("endDate", LocalDate.of(2022,12,30));

    ExtractableResponse<Response> response = RestAssured
        .given()
        .log().all()
        .body(requestDto)
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .when()
        .post(baseUrl + "/travel")
        .then()
        .log().all()
        .extract();

    assertThat(response.statusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());

  }

  @DisplayName("여행 등록시 종료일이 출발일보다 빠른 경운 오류 발생")
  @Test
  void createCityDateException(){
    Map<String, Object> requestDto = new HashMap<>();
    requestDto.put("cityName", "부산");
    requestDto.put("country", "대한민국");
    requestDto.put("userId", 1L);
    requestDto.put("startDate", LocalDate.of(2022,12,25));
    requestDto.put("endDate", LocalDate.of(2022,12,15));

    ExtractableResponse<Response> response = RestAssured
        .given()
        .log().all()
        .body(requestDto)
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .when()
        .post(baseUrl + "/travel")
        .then()
        .log().all()
        .extract();

    assertThat(response.statusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());

  }

  @DisplayName("여행 수정")
  @Test
  void updateTravel(){

    Map<String, Object> requestDto = new HashMap<>();
    requestDto.put("cityName", "부산");
    requestDto.put("country", "대한민국");
    requestDto.put("userId", 1L);
    requestDto.put("startDate", LocalDate.of(2022,12,25));
    requestDto.put("endDate", LocalDate.of(2022,12,30));

    ExtractableResponse<Response> response = RestAssured
        .given()
        .log().all()
        .body(requestDto)
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .when()
        .post(baseUrl + "/travel/1")
        .then()
        .log().all()
        .extract();

    TravelInfo responseInfo = response.jsonPath().getObject("data", TravelInfo.class);

    assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    assertThat(responseInfo).isNotNull();
    assertThat(responseInfo.cityInfo().cityName()).isEqualTo("부산");
    assertThat(responseInfo.cityInfo().id()).isEqualTo(3L);
  }

  @DisplayName("여행 삭제")
  @Test
  void deleteTravel(){
    ExtractableResponse<Response> response = RestAssured
        .given()
        .log().all()
        .when()
        .delete(baseUrl + "/travel/1")
        .then()
        .log().all()
        .extract();

    assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
  }

  @DisplayName("없는 여행 삭제시 오류발생")
  @Test
  void deleteTravelException(){
    ExtractableResponse<Response> response = RestAssured
        .given()
        .log().all()
        .when()
        .delete(baseUrl + "/travel/11")
        .then()
        .log().all()
        .extract();

    assertThat(response.statusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
  }
}