package com.triple.travelmanage.common.exception;

import com.triple.travelmanage.city.exception.CityException;
import com.triple.travelmanage.common.response.ResponseDto;
import com.triple.travelmanage.travel.exception.TravelException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class TravelManageExceptionHandler {

  /**
   * 지원하지 않는 HTTP Method 요청시 Exception 핸들링
   */
  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  public ResponseEntity<ResponseDto<?>> handleException(
      HttpRequestMethodNotSupportedException ex) {
    log.warn("", ex);

    return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
        .body(ResponseDto.error(ex.getMessage()));
  }

  /**
   * 서버 내부에서 예상하지 못한 예외 발생시 Exception 핸들링
   */
  @ExceptionHandler(value = Exception.class)
  public ResponseEntity<ResponseDto<?>> handleException(Exception ex) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(ResponseDto.error(ErrorMessage.UNKNOWN.getMessage()));
  }


  /**
   * 서버 내부에서 예상하지 못한 예외 발생시 Exception 핸들링
   */
  @ExceptionHandler(value = {IllegalArgumentException.class, IllegalStateException.class})
  public ResponseEntity<ResponseDto<?>> handleIllegalArgumentException(Exception ex) {
    log.warn("", ex);

    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(ResponseDto.error(ex.getMessage()));
  }


  @ExceptionHandler(value = CityException.class)
  public ResponseEntity<ResponseDto<?>> handleCityException(CityException e) {
    log.warn("", e);

    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(ResponseDto.error(e.getMessage(), "CityException"));
  }


  @ExceptionHandler(value = TravelException.class)
  public ResponseEntity<ResponseDto<?>> handleTravelException(TravelException e) {
    log.warn("", e);

    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(ResponseDto.error(e.getMessage(), "TravelException"));
  }


}