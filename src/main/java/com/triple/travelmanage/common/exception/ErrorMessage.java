package com.triple.travelmanage.common.exception;

import lombok.Getter;

public enum ErrorMessage {

  //common
  UNKNOWN("알 수 없는 오류가 발생했습니다."),
  NOT_EXIST_VALID_CITY("도시 정보가 존재하지 않습니다."),
  DELETE_HISTORY_ERROR("삭제할 조회 이력 정보가 없습니다."),

  NOT_EXIST_CITY("해당 도시가 존재하지 않습니다."),
  NOT_EXIST_TRAVEL("해당 여행이 존재하지 않습니다."),
  EXIST_CITY_ERROR("동일한 도시가 존재합니다"),
  DELETE_ERROR("해당 도시는 여행지에 포함되어 있어 삭제가 불가능하다.");
  @Getter
  private final String message; // 사용자에게 노출될 메세지

  ErrorMessage(String message) {
    this.message = message;
  }
}