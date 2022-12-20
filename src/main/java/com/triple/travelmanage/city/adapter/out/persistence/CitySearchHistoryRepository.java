package com.triple.travelmanage.city.adapter.out.persistence;

import jakarta.persistence.LockModeType;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CitySearchHistoryRepository extends JpaRepository<CitySearchHistoryEntity, Long> {

  Optional<CitySearchHistoryEntity> findByCityIdAndUserIdAndDeletedAtIsNull(Long cityId, Long userId);
  Optional<CitySearchHistoryEntity> findByIdAndDeletedAtIsNull(Long id);

  @Lock(LockModeType.PESSIMISTIC_WRITE)
  @Query("select c from CitySearchHistoryEntity c where c.deletedAt is null and c.id = :id")
  Optional<CitySearchHistoryEntity> findForUpdate(@Param("id") Long id);

  List<CitySearchHistoryEntity> findAllByUserIdAndCityIdInAndDeletedAtIsNull(Long userId, List<Long> cityIds);
}
