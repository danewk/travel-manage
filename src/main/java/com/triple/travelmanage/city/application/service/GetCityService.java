package com.triple.travelmanage.city.application.service;

import com.triple.travelmanage.city.application.port.in.CityInfo;
import com.triple.travelmanage.city.application.port.in.GetCityUseCase;
import com.triple.travelmanage.city.application.port.in.command.CityRetrieveCommand;
import com.triple.travelmanage.city.application.port.out.GetCityPort;
import com.triple.travelmanage.city.application.port.out.GetCitySearchHistoryPort;
import com.triple.travelmanage.city.application.port.out.GetTravelPort;
import com.triple.travelmanage.city.domain.CitySearchHistory;
import com.triple.travelmanage.common.annotation.UseCase;
import com.triple.travelmanage.travel.application.port.in.TravelInfo;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class GetCityService implements GetCityUseCase {

  private final GetCityPort getCityPort;
  private final GetTravelPort getTravelPort;
  private final GetCitySearchHistoryPort getCitySearchHistoryPort;

  @Override
  public List<CityInfo> getCities(Long userId) {

    List<CityInfo> temp = new ArrayList<>();

    List<TravelInfo> travelInfos = getTravelPort.findAllByUserId(userId);

    //여행중인 도시 찾기
    List<CityInfo> cityInTraveling = travelInfos.stream().filter(this::traveling)
        .sorted(Comparator.comparing(TravelInfo::startDate))
        .map(TravelInfo::cityInfo)
        .toList();

    //여행이 예정된 도시
    filterScheduledTravel(temp, travelInfos);

    //하루 이내에 등록된 도시
    filterRegisteredWithinOneDay(temp, travelInfos);

    //최근 일주일 이내에 한번 이상 조회된 도시 - 가장 최근에 조회한것 부터
    filterMoreThanOnceWithinAWeek(userId, temp, travelInfos);

    //위의 조건에 해당되지 않는 도시 - 무작위
    filterIrrelevant(temp, travelInfos);

    List<CityInfo> results = new ArrayList<>(temp.stream().distinct()
        .limit(10L)
        .toList());

    results.addAll(0, cityInTraveling);

    return results;
  }

  private static void filterIrrelevant(List<CityInfo> result, List<TravelInfo> travelInfos) {
    List<CityInfo> irrelevantCityInfos = new ArrayList<>(travelInfos.stream()
        .map(TravelInfo::cityInfo)
        .filter(cityInfo -> !result.contains(cityInfo))
        .toList());

    Collections.shuffle(irrelevantCityInfos);

    result.addAll(irrelevantCityInfos);
  }

  private void filterMoreThanOnceWithinAWeek(Long userId, List<CityInfo> result, List<TravelInfo> travelInfos) {
    List<CitySearchHistory> searchHistoryList = getCitySearchHistoryPort.getSearchHistoryByUserIdAndCityIds(userId, travelInfos);

    List<Long> moreThanOnceWithinAWeekCityIds = searchHistoryList.stream()
        .filter(this::validCreatedAtAndCount)
        .sorted(Comparator.comparing(CitySearchHistory::getCreatedAt).reversed())
        .map(CitySearchHistory::getCityId).toList();

    List<CityInfo> CityInfoMoreThanOnceWithinAWeek = travelInfos.stream().map(TravelInfo::cityInfo)
        .filter(cityInfo -> matchHistory(cityInfo, moreThanOnceWithinAWeekCityIds))
        .toList();

    result.addAll(CityInfoMoreThanOnceWithinAWeek);
  }

  private void filterRegisteredWithinOneDay(List<CityInfo> result, List<TravelInfo> travelInfos) {
    List<CityInfo> cityInRegisteredWithinOneDay = travelInfos.stream().map(TravelInfo::cityInfo)
        .filter(this::registeredWithinOneDay)
        .sorted(Comparator.comparing(CityInfo::createdAt).reversed())
        .toList();

    result.addAll(cityInRegisteredWithinOneDay);
  }

  private void filterScheduledTravel(List<CityInfo> result, List<TravelInfo> travelInfos) {
    List<CityInfo> cityInScheduledTravel = travelInfos.stream().filter(this::scheduledTravel)
        .sorted(Comparator.comparing(TravelInfo::startDate))
        .map(TravelInfo::cityInfo)
        .toList();

    result.addAll(cityInScheduledTravel);
  }

  private boolean matchHistory(CityInfo cityInfo, List<Long> moreThanOnceWithinAWeekCityIds) {
    return moreThanOnceWithinAWeekCityIds.stream()
        .anyMatch(id -> id.equals(cityInfo.id()));
  }

  private boolean validCreatedAtAndCount(CitySearchHistory citySearchHistory) {
    return citySearchHistory.getCreatedAt().isAfter(LocalDateTime.now().minusDays(7)) && citySearchHistory.getCount() > 0;

  }

  private boolean registeredWithinOneDay(CityInfo cityInfo) {
    return cityInfo.createdAt().isAfter(LocalDateTime.now().minusDays(1));
  }

  private boolean scheduledTravel(TravelInfo travelInfo) {
    return travelInfo.startDate().isAfter(LocalDate.now());
  }

  private boolean traveling(TravelInfo travelInfo) {
    return travelInfo.startDate().isBefore(LocalDate.now()) && travelInfo.endDate().isAfter(LocalDate.now());
  }

  @Override
  public CityInfo getCity(final Long cityId) {
    return CityInfo.from(getCityPort.getCityById(cityId));
  }


}
