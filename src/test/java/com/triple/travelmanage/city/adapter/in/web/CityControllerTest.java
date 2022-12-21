package com.triple.travelmanage.city.adapter.in.web;

import static com.triple.travelmanage.common.fixture.city.CityEntityFixture.경주_도시_엔티티;
import static com.triple.travelmanage.common.fixture.city.CityEntityFixture.부산_도시_엔티티;
import static com.triple.travelmanage.common.fixture.city.CityEntityFixture.서울_도시_엔티티;
import static com.triple.travelmanage.common.fixture.city.CityEntityFixture.제주도_도시_엔티티;
import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.triple.travelmanage.city.adapter.out.persistence.CityRepository;
import com.triple.travelmanage.city.application.port.in.CityInfo;
import com.triple.travelmanage.common.BaseIntegrationTest;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
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

class CityControllerTest extends BaseIntegrationTest {

  private final String baseUrl = "/api/v1";
  @Autowired
  CityRepository cityRepository;

  @Autowired
  private ObjectMapper objectMapper;

  @BeforeEach
  void init() {
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
    cityRepository.deleteAll();
  }

  @DisplayName("도시 단건 조회")
  @Test
  void getCity() {

    Map<String, Long> requestDto = new HashMap<>();
    requestDto.put("userId", 1L);

    ExtractableResponse<Response> response = RestAssured
        .given()
        .log().all()
        .body(requestDto)
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .when()
        .post(baseUrl + "/cities/1")
        .then()
        .log().all()
        .extract();

    CityInfo cityInfo = response.jsonPath().getObject("data", CityInfo.class);

    assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    assertThat(cityInfo.cityName()).isEqualTo("서울");

  }

  @DisplayName("도시 수정")
  @Test
  void updateCity(){
    Map<String, String> requestDto = new HashMap<>();
    requestDto.put("cityName", "강릉");
    requestDto.put("country", "대한민국");

    ExtractableResponse<Response> response = RestAssured
        .given()
        .log().all()
        .body(requestDto)
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .when()
        .put(baseUrl + "/cities/1")
        .then()
        .log().all()
        .extract();

    CityInfo responseInfo = response.jsonPath().getObject("data", CityInfo.class);


    assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    assertThat(responseInfo.id()).isEqualTo(1L);
    assertThat(responseInfo.cityName()).isEqualTo("강릉");

  }

  @DisplayName("존재하지 않는 도시 조회시 예외 발생")
  @Test
  void getCityException(){
    Map<String, Long> requestDto = new HashMap<>();
    requestDto.put("userId", 1L);

    ExtractableResponse<Response> response = RestAssured
        .given()
        .log().all()
        .body(requestDto)
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .when()
        .post(baseUrl + "/cities/10")
        .then()
        .log().all()
        .extract();


    assertThat(response.statusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
  }

  @DisplayName("도시 등록")
  @Test
  void createCity(){
    Map<String, String> requestDto = new HashMap<>();
    requestDto.put("cityName", "여수");
    requestDto.put("country", "대한민국");

    ExtractableResponse<Response> response = RestAssured
        .given()
        .log().all()
        .body(requestDto)
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .when()
        .post(baseUrl + "/city")
        .then()
        .log().all()
        .extract();

    CityInfo responseInfo = response.jsonPath().getObject("data", CityInfo.class);



    assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    assertThat(responseInfo).isNotNull();
    assertThat(responseInfo.cityName()).isEqualTo("여수");

  }

  @DisplayName("기존에 등록된 도시 정보로 등록시 예외발생")
  @Test
  void createCityException(){
    Map<String, String> requestDto = new HashMap<>();
    requestDto.put("cityName", "부산");
    requestDto.put("country", "대한민국");

    ExtractableResponse<Response> response = RestAssured
        .given()
        .log().all()
        .body(requestDto)
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .when()
        .post(baseUrl + "/city")
        .then()
        .log().all()
        .extract();

    assertThat(response.statusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());

  }

  @DisplayName("도시 삭제")
  @Test
  void delete(){
    ExtractableResponse<Response> response = RestAssured
        .given()
        .log().all()
        .when()
        .delete(baseUrl + "/cities/1")
        .then()
        .log().all()
        .extract();

    assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());

  }

  @DisplayName("없는 도시 삭제시 예외발생")
  @Test
  void deleteException(){
    ExtractableResponse<Response> response = RestAssured
        .given()
        .log().all()
        .when()
        .delete(baseUrl + "/cities/11")
        .then()
        .log().all()
        .extract();

    assertThat(response.statusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());

  }

}