package com.example.deliveryfeecalculator.repository;

import com.example.deliveryfeecalculator.model.Station;
import org.springframework.data.repository.CrudRepository;

public interface StationRepository extends CrudRepository<Station, Long> {
    /**
     * @param cityName Name of the city.
     * @return Weather station of the given city.
     */
    Station findByCityNameIgnoreCase(String cityName);
}
