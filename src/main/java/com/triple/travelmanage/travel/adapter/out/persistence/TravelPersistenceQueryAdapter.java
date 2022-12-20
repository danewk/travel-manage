
package com.triple.travelmanage.travel.adapter.out.persistence;

import com.triple.travelmanage.common.annotation.PersistenceAdapter;
import com.triple.travelmanage.common.exception.ErrorMessage;
import com.triple.travelmanage.travel.application.port.out.GetTravelPort;
import com.triple.travelmanage.travel.domain.Travel;
import com.triple.travelmanage.travel.exception.TravelException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@PersistenceAdapter
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TravelPersistenceQueryAdapter implements GetTravelPort {

  private final TravelRepository travelRepository;

  @Override
  public Travel getTravelById(Long travelId) {
    return travelRepository.findByIdAndDeletedAtIsNull(travelId).orElseThrow(() -> new TravelException(ErrorMessage.NOT_EXIST_TRAVEL.getMessage())).toDomain();
  }

  @Override
  public List<Travel> getTravelsByCityId(Long cityId) {
    return travelRepository.findTravelsByCityId(cityId).stream()
        .map(TravelEntity::toDomain).toList();

  }

  @Override
  public List<Travel> getTravelsByUserId(Long userId) {
    return travelRepository.findAllByUserIdAndDeletedAtIsNull(userId).stream()
        .map(TravelEntity::toDomain).toList();
  }
}
