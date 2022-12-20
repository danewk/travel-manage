package com.triple.travelmanage.city.application.service;

import com.triple.travelmanage.city.application.port.in.DeleteCityUseCase;
import com.triple.travelmanage.city.application.port.out.DeleteCityPort;
import com.triple.travelmanage.city.application.port.out.GetTravelPort;
import com.triple.travelmanage.city.application.port.out.GetCityPort;
import com.triple.travelmanage.city.domain.City;
import com.triple.travelmanage.city.exception.CityException;
import com.triple.travelmanage.common.annotation.UseCase;
import com.triple.travelmanage.common.exception.ErrorMessage;
import com.triple.travelmanage.travel.application.port.in.TravelInfo;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.util.CollectionUtils;

@UseCase
@RequiredArgsConstructor
public class DeleteCityService implements DeleteCityUseCase {

  private final DeleteCityPort deleteCityPort;
  private final GetCityPort getCityPort;
  private final GetTravelPort getTravelPort;
  @Override
  public void deleteCity(Long cityId) {
    List<TravelInfo> travels = getTravelPort.findAllByCityId(cityId);

    if(!CollectionUtils.isEmpty(travels)){
      throw new CityException(ErrorMessage.DELETE_ERROR.getMessage());
    }

    City savedCity = getCityPort.getCityById(cityId);
    savedCity.delete();
    deleteCityPort.delete(savedCity);
  }
}
