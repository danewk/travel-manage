package com.triple.travelmanage.city.application.port.in.command;

import lombok.Builder;

@Builder
public record SyncHistoryCommand(
    Long cityId,
    Long userId
) {

  public static SyncHistoryCommand of(Long cityId, Long userId){
    return SyncHistoryCommand.builder()
        .cityId(cityId)
        .userId(userId)
        .build();
  }

}
