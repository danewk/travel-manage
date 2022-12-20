package com.triple.travelmanage.city.adapter.out.persistence;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityRepository extends JpaRepository<CityEntity, Long> {
  Optional<CityEntity> findByIdAndDeletedAtIsNull(Long id);

  Optional<CityEntity> findByCityNameAndCountryAndDeletedAtIsNull(String cityName, String country);
}
