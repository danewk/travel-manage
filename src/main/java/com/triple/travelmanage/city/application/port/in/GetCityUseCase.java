package com.triple.travelmanage.city.application.port.in;

import com.triple.travelmanage.city.adapter.in.web.CityRetrieveRequestDto;
import com.triple.travelmanage.city.application.port.in.command.CityRetrieveCommand;
import jakarta.validation.Valid;
import java.util.List;

public interface GetCityUseCase {

  List<CityInfo> getCities(Long userId);

  CityInfo getCity(Long cityId);

}
