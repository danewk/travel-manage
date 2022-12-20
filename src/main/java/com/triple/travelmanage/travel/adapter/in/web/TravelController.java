package com.triple.travelmanage.travel.adapter.in.web;

import com.triple.travelmanage.common.annotation.WebAdapter;
import com.triple.travelmanage.common.response.ResponseDto;
import com.triple.travelmanage.travel.application.port.in.CreateTravelUseCase;
import com.triple.travelmanage.travel.application.port.in.DeleteTravelUseCase;
import com.triple.travelmanage.travel.application.port.in.GetTravelUseCase;
import com.triple.travelmanage.travel.application.port.in.TravelInfo;
import com.triple.travelmanage.travel.application.port.in.UpdateTravelUseCase;
import com.triple.travelmanage.travel.application.port.in.command.TravelCreateCommand;
import com.triple.travelmanage.travel.application.port.in.command.TravelUpdateCommand;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@WebAdapter(path = "/api/v1")
@RequiredArgsConstructor
public class TravelController {

  private final CreateTravelUseCase createTravelUseCase;
  private final UpdateTravelUseCase updateTravelUseCase;
  private final GetTravelUseCase getTravelUseCase;
  private final DeleteTravelUseCase deleteTravelUseCase;

  @Operation(tags = "Travel", summary = "여행 등록", responses = {
      @ApiResponse(responseCode = "200", description = "성공")
  })
  @PostMapping("/travel")
  public ResponseDto<?> registerTravel(
      @Valid @RequestBody TravelCreateRequestDto requestDto
  ) {
    createTravelUseCase.createTravel(TravelCreateCommand.of(requestDto));
    return ResponseDto.ok();
  }

  @Operation(tags = "Travel", summary = "여행 수정", responses = {
      @ApiResponse(responseCode = "200", description = "성공")
  })
  @PostMapping("/travel/{travelId}")
  public ResponseDto<?> updateCityInfo(
      @PathVariable("travelId") Long travelId,
      @Valid @RequestBody TravelUpdateRequestDto travelUpdateRequestDto
  ) {
    updateTravelUseCase.updateTravel(TravelUpdateCommand.of(travelUpdateRequestDto, travelId));
    return ResponseDto.ok();
  }

  @Operation(tags = "Travel", summary = "여행 단건 조회", responses = {
      @ApiResponse(responseCode = "200", description = "성공")
  })
  @GetMapping("/travel/{travelId}")
  public ResponseDto<TravelInfo> getTravel(@PathVariable("travelId") Long travelId) {
    return ResponseDto.ok(getTravelUseCase.getTravel(travelId));
  }

  @Operation(tags = "Travel", summary = "여행 삭제", responses = {
      @ApiResponse(responseCode = "200", description = "성공")
  })
  @DeleteMapping("/travel/{travelId}")
  public ResponseDto<?> deleteTravel(@PathVariable("travelId") Long travelId) {
    deleteTravelUseCase.deleteTravel(travelId);
    return ResponseDto.ok();
  }
}
