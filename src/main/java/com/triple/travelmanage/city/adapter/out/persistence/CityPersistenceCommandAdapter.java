package com.triple.travelmanage.city.adapter.out.persistence;

import com.triple.travelmanage.city.application.port.out.CreateCityPort;
import com.triple.travelmanage.city.application.port.out.DeleteCityPort;
import com.triple.travelmanage.city.application.port.out.UpdateCityPort;
import com.triple.travelmanage.city.domain.City;
import com.triple.travelmanage.city.exception.CityException;
import com.triple.travelmanage.common.annotation.PersistenceAdapter;
import com.triple.travelmanage.common.exception.ErrorMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@PersistenceAdapter
@RequiredArgsConstructor
@Transactional
public class CityPersistenceCommandAdapter implements CreateCityPort, UpdateCityPort, DeleteCityPort {

  private final CityRepository cityRepository;

  @Override
  public City create(City city) {
    CityEntity cityEntity = CityEntity.from(city);
    return cityRepository.save(cityEntity).toDomain();
  }

  @Override
  public void update(City city) {
    CityEntity savedCityEntity = getSavedCityEntity(city);
    savedCityEntity.update(city);
  }

  @Override
  public void delete(City city) {
    CityEntity savedCityEntity = getSavedCityEntity(city);
    savedCityEntity.softDelete(city.getDeletedAt());
  }

  private CityEntity getSavedCityEntity(City city) {
    return cityRepository.findByIdAndDeletedAtIsNull(city.getId())
        .orElseThrow(() -> new CityException(ErrorMessage.NOT_EXIST_VALID_CITY.getMessage()));
  }
}
