package com.triple.travelmanage.city.adapter.out.persistence;

import com.triple.travelmanage.city.application.port.out.GetCityPort;
import com.triple.travelmanage.city.domain.City;
import com.triple.travelmanage.city.exception.CityException;
import com.triple.travelmanage.common.annotation.PersistenceAdapter;
import com.triple.travelmanage.common.exception.ErrorMessage;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@PersistenceAdapter
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CityPersistenceQueryAdapter implements GetCityPort {

  private final CityRepository cityRepository;

  @Override
  public City getCityById(Long cityId) {
    return cityRepository.findByIdAndDeletedAtIsNull(cityId).orElseThrow(() -> new CityException(ErrorMessage.NOT_EXIST_CITY.getMessage())).toDomain();
  }

  @Override
  public City getCityByName(String cityName, String country) {
    return cityRepository.findByCityNameAndCountryAndDeletedAtIsNull(cityName, country)
        .orElseThrow(() -> new CityException(ErrorMessage.NOT_EXIST_CITY.getMessage()))
        .toDomain();
  }

  @Override
  public City getCityForCreate(String cityName, String country) {
    Optional<CityEntity> city = cityRepository.findByCityNameAndCountryAndDeletedAtIsNull(cityName, country);
    if(city.isPresent()){
      return city.get().toDomain();
    }
    return City.builder()
        .country("")
        .cityName("")
        .build();
  }
}
