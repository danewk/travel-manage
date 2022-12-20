package com.triple.travelmanage.city.application.service;

import com.triple.travelmanage.city.application.port.in.CityInfo;
import com.triple.travelmanage.city.application.port.in.UpdateCityUseCase;
import com.triple.travelmanage.city.application.port.in.command.CityUpdateCommand;
import com.triple.travelmanage.city.application.port.out.GetCityPort;
import com.triple.travelmanage.city.application.port.out.UpdateCityPort;
import com.triple.travelmanage.city.domain.City;
import com.triple.travelmanage.city.exception.CityException;
import com.triple.travelmanage.common.annotation.UseCase;
import com.triple.travelmanage.common.exception.ErrorMessage;
import java.util.Objects;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class UpdateCityService implements UpdateCityUseCase {

  private final UpdateCityPort updateCityPort;
  private final GetCityPort getCityPort;

  @Override
  public CityInfo updateCity(CityUpdateCommand command) {
    City city = getCityPort.getCityForCreate(command.cityName(), command.country());

    if(Objects.nonNull(city.getId())){
      throw new CityException(ErrorMessage.EXIST_CITY_ERROR.getMessage());
    }

    City savedCity = getCityPort.getCityById(command.cityId());
    savedCity.update(command.toDomain());
    updateCityPort.update(savedCity);

    return CityInfo.from(savedCity);

  }
}
