package com.triple.travelmanage.city.adapter.in.web;

import com.triple.travelmanage.city.application.port.in.CityInfo;
import com.triple.travelmanage.city.application.port.in.CreateCityUseCase;
import com.triple.travelmanage.city.application.port.in.DeleteCityUseCase;
import com.triple.travelmanage.city.application.port.in.GetCityUseCase;
import com.triple.travelmanage.city.application.port.in.SyncCitySearchHistoryUseCase;
import com.triple.travelmanage.city.application.port.in.UpdateCityUseCase;
import com.triple.travelmanage.city.application.port.in.command.CityCreateCommand;
import com.triple.travelmanage.city.application.port.in.command.CityUpdateCommand;
import com.triple.travelmanage.city.application.port.in.command.SyncHistoryCommand;
import com.triple.travelmanage.common.annotation.WebAdapter;
import com.triple.travelmanage.common.response.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@WebAdapter(path = "/api/v1")
@RequiredArgsConstructor
public class CityController {

  private final GetCityUseCase getCityUseCase;
  private final CreateCityUseCase createCityUseCase;
  private final UpdateCityUseCase updateCityUseCase;
  private final DeleteCityUseCase deleteCityUseCase;
  private final SyncCitySearchHistoryUseCase syncCitySearchHistoryUseCase;

  @Operation(tags = "City", summary = "사용자별 도시 목록 조회", responses = {
      @ApiResponse(responseCode = "200", description = "성공",
          content = @Content(schema = @Schema(implementation = CityInfo.class))
      )
  })
  @GetMapping("/user/{userId}/cities")
  public ResponseDto<List<CityInfo>> getCities(
      @PathVariable("userId") Long userId
  ) {
    return ResponseDto.ok(getCityUseCase.getCities(userId));
  }

  @Operation(tags = "City", summary = "도시 등록", responses = {
      @ApiResponse(responseCode = "200", description = "성공")
  })
  @PostMapping("/city")
  public ResponseDto<?> registerCity(
      @Valid @RequestBody CityCreateRequestDto requestDto
  ) {
    return ResponseDto.ok(createCityUseCase.createCity(CityCreateCommand.of(requestDto)));
  }

  @Operation(tags = "City", summary = "도시 단건 조회", responses = {
      @ApiResponse(responseCode = "200", description = "성공")
  })
  @PostMapping("/cities/{cityId}")
  public ResponseDto<CityInfo> getCity(@PathVariable("cityId") Long cityId,
      @Valid @RequestBody CityRetrieveRequestDto requestDto
  ) {
    syncCitySearchHistoryUseCase.syncHistory(SyncHistoryCommand.of(cityId, requestDto.userId()));
    return ResponseDto.ok(getCityUseCase.getCity(cityId));
  }

  @Operation(tags = "City", summary = "도시 수정", responses = {
      @ApiResponse(responseCode = "200", description = "성공")
  })
  @PutMapping("/cities/{cityId}")
  public ResponseDto<?> updateCityInfo(
      @PathVariable("cityId") Long cityId,
      @Valid @RequestBody CityUpdateRequestDto cityUpdateRequestDto
  ) {
    return ResponseDto.ok(updateCityUseCase.updateCity(CityUpdateCommand.of(cityUpdateRequestDto,cityId)));
  }

  @Operation(tags = "City", summary = "도시 삭제", responses = {
      @ApiResponse(responseCode = "200", description = "성공")
  })
  @DeleteMapping("/cities/{cityId}")
  public ResponseDto<?> deleteCityInfo(@PathVariable("cityId") Long cityId) {
    deleteCityUseCase.deleteCity(cityId);
    return ResponseDto.ok();
  }




}
