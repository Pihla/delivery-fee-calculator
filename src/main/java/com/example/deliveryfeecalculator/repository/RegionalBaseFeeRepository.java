package com.example.deliveryfeecalculator.repository;

import com.example.deliveryfeecalculator.model.RegionalBaseFee;
import com.example.deliveryfeecalculator.model.Station;
import com.example.deliveryfeecalculator.model.VehicleType;
import org.springframework.data.repository.CrudRepository;

public interface RegionalBaseFeeRepository extends CrudRepository<RegionalBaseFee, Long> {
    /**
     * @param station Weather station.
     * @param vehicleType Enum representing the vehicle type.
     * @return Regional base fee corresponding to the station and vehicle type.
     */
    RegionalBaseFee findRegionalBaseFeeByStationAndVehicleType(Station station, VehicleType vehicleType);
}
