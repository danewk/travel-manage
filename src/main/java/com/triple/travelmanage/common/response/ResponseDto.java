package com.triple.travelmanage.common.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ResponseDto<T> {

  private boolean ok;

  @JsonIgnore
  private Integer status;

  private T data;

  private Error error;

  @JsonIgnore
  private List<Validation> validations;

  private ResponseDto(boolean ok, Integer status, @Nullable T data,
      @Nullable Error error, @Nullable List<Validation> validations) {
    super();
    this.ok = ok;
    this.status = status;
    this.data = data;
    this.error = error;
    this.validations = validations;
    if (this.error != null) {
      this.error.setValidations(validations);
    }
  }


  public static <T> ResponseDto<T> ok() {
    return new ResponseDto<>(true, HttpStatus.OK.value(), null, null, null);
  }

  public static <T> ResponseDto<T> ok(T data) {
    return new ResponseDto<>(true, HttpStatus.OK.value(), data, null, null);
  }

  public static <T> ResponseDto<T> badRequest(String message) {
    return badRequest(message, null);
  }

  public static <T> ResponseDto<T> badRequest(String message, List<Validation> validations) {
    Error error = ResponseDto.Error.builder()
        .code(-1)
        .message(message)
        .build();

    return new ResponseDto<>(false, HttpStatus.BAD_REQUEST.value(), null, error, validations);
  }

  public static <T> ResponseDto<T> unauthorized(String message) {
    Error error = ResponseDto.Error.builder()
        .code(-1)
        .message(message)
        .build();

    return new ResponseDto<>(false, HttpStatus.UNAUTHORIZED.value(), null, error, null);
  }

  public static <T> ResponseDto<T> notFound(String message) {
    Error error = ResponseDto.Error.builder()
        .code(-1)
        .message(message)
        .build();

    return new ResponseDto<>(false, HttpStatus.NOT_FOUND.value(), null, error, null);
  }

  public static <T> ResponseDto<T> error(String message) {
    Error error = ResponseDto.Error.builder()
        .code(-1)
        .message(message)
        .build();

    return new ResponseDto<>(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), null, error, null);
  }

  public static <T> ResponseDto<T> error(String message, String type) {
    Error error = Error.builder()
        .code(-1)
        .message(message)
        .type(type)
        .build();

    return new ResponseDto<>(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), null, error, null);
  }

  public static <T> ResponseDto<T> error(String message, int code) {
    Error error = ResponseDto.Error.builder()
        .code(code)
        .message(message)
        .build();

    return new ResponseDto<>(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), null, error, null);
  }


  @NoArgsConstructor(access = AccessLevel.PROTECTED)
  @Getter
  public static class Error {

    private int code;

    private String message;

    private String type;

    private List<Validation> validations;

    @Builder
    public Error(int code, String message, String type, @Nullable List<Validation> validations) {
      super();
      this.code = code;
      this.type = type;
      this.message = message;
      setValidations(validations);
    }

    public void setValidations(@Nullable List<Validation> validations) {
      this.validations = validations;
    }
  }

  @NoArgsConstructor(access = AccessLevel.PROTECTED)
  @Getter
  public static class Validation {

    private String field;

    private String message;


    @Builder
    public Validation(String field, String message) {
      super();
      this.field = field;
      this.message = message;
    }
  }
}