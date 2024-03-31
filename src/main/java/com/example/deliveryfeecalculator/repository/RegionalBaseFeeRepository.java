package com.example.deliveryfeecalculator.repository;

import com.example.deliveryfeecalculator.model.RegionalBaseFee;
import com.example.deliveryfeecalculator.model.Station;
import com.example.deliveryfeecalculator.model.Vehicle;
import org.springframework.data.repository.CrudRepository;

public interface RegionalBaseFeeRepository extends CrudRepository<RegionalBaseFee, Long> {
    RegionalBaseFee findRegionalBaseFeeByStationAndVehicle(Station station, Vehicle vehicle);
}
