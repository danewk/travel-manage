
package com.triple.travelmanage.travel.adapter.out.persistence;

import com.triple.travelmanage.common.annotation.PersistenceAdapter;
import com.triple.travelmanage.common.exception.ErrorMessage;
import com.triple.travelmanage.travel.application.port.out.CreateTravelPort;
import com.triple.travelmanage.travel.application.port.out.DeleteTravelPort;
import com.triple.travelmanage.travel.application.port.out.UpdateTravelPort;
import com.triple.travelmanage.travel.domain.Travel;
import com.triple.travelmanage.travel.exception.TravelException;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@PersistenceAdapter
@RequiredArgsConstructor
@Transactional
public class TravelPersistenceCommandAdapter implements CreateTravelPort, DeleteTravelPort, UpdateTravelPort {

  private final TravelRepository travelRepository;

  @Override
  public Travel create(Travel travel) {
    return travelRepository.save(TravelEntity.from(travel)).toDomain();
  }

  @Override
  public void delete(Travel travel) {
    TravelEntity travelEntity = getTravelEntity(travel);
    travelEntity.softDelete(travel.getDeletedAt());
  }

  @Override
  public void update(Travel savedTravel) {
    TravelEntity travelEntity = getTravelEntity(savedTravel);
    travelEntity.update(savedTravel);
  }

  private TravelEntity getTravelEntity(Travel savedTravel) {
    return travelRepository.findByIdAndDeletedAtIsNull(savedTravel.getId())
        .orElseThrow(() -> new TravelException(ErrorMessage.NOT_EXIST_TRAVEL.getMessage()));
  }
}
