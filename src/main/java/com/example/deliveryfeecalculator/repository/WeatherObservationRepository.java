package com.example.deliveryfeecalculator.repository;


import com.example.deliveryfeecalculator.model.Station;
import com.example.deliveryfeecalculator.model.WeatherObservation;
import org.springframework.data.repository.CrudRepository;

public interface WeatherObservationRepository extends CrudRepository<WeatherObservation, Long> {

    WeatherObservation findTop1ByStationOrderByTimestampDesc(Station station);
}
