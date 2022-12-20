package com.triple.travelmanage.travel.adapter.out.persistence;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TravelRepository extends JpaRepository<TravelEntity, Long> {

  Optional<TravelEntity> findByIdAndDeletedAtIsNull(Long id);

  @Query("select t from TravelEntity t where t.deletedAt is null and t.cityId = :cityId")
  List<TravelEntity> findTravelsByCityId(@Param("cityId") Long cityId);

  List<TravelEntity> findAllByUserIdAndDeletedAtIsNull(Long userId);
}
