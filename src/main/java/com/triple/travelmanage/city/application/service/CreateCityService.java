package com.triple.travelmanage.city.application.service;

import com.triple.travelmanage.city.application.port.in.CityInfo;
import com.triple.travelmanage.city.application.port.in.CreateCityUseCase;
import com.triple.travelmanage.city.application.port.in.command.CityCreateCommand;
import com.triple.travelmanage.city.application.port.out.CreateCityPort;
import com.triple.travelmanage.city.application.port.out.GetCityPort;
import com.triple.travelmanage.city.domain.City;
import com.triple.travelmanage.city.exception.CityException;
import com.triple.travelmanage.common.annotation.UseCase;
import com.triple.travelmanage.common.exception.ErrorMessage;
import java.util.Objects;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class CreateCityService implements CreateCityUseCase {

  private final CreateCityPort createCityPort;
  private final GetCityPort getCityPort;

  @Override
  public CityInfo createCity(CityCreateCommand command) {
    validationExistCity(command);
    City city = command.toDomain();
    return CityInfo.from(createCityPort.create(city));
  }

  private void validationExistCity(CityCreateCommand command) {
    City savedCity = getCityPort.getCityForCreate(command.cityName(), command.country());

    if(Objects.nonNull(savedCity.getId())){
      throw new CityException(ErrorMessage.EXIST_CITY_ERROR.getMessage());
    }
  }
}
