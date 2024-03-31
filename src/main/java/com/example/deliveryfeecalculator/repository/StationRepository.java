package com.example.deliveryfeecalculator.repository;

import com.example.deliveryfeecalculator.model.Station;
import org.springframework.data.repository.CrudRepository;

public interface StationRepository extends CrudRepository<Station, Long> {
    Station findByCityNameIgnoreCase(String cityName);
}
