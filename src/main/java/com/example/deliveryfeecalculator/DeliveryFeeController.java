package com.example.deliveryfeecalculator;

import com.example.deliveryfeecalculator.model.Station;
import com.example.deliveryfeecalculator.model.Vehicle;
import com.example.deliveryfeecalculator.repository.StationRepository;
import com.example.deliveryfeecalculator.service.DeliveryFeeCalculatingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/deliveryFee")
public class DeliveryFeeController {
    DeliveryFeeCalculatingService deliveryFeeCalculatingService;
    StationRepository stationRepository;

    DeliveryFeeController(DeliveryFeeCalculatingService deliveryFeeCalculatingService, StationRepository stationRepository) {
        this.deliveryFeeCalculatingService = deliveryFeeCalculatingService;
        this.stationRepository = stationRepository;
    }
    @GetMapping()
    ResponseEntity<?> getDeliveryFee(String city, String vehicleType) {
        Station station = stationRepository.findByCityNameIgnoreCase(city);
        Vehicle vehicle = Vehicle.valueOf(vehicleType.toUpperCase());
        try {
            return ResponseEntity.ok(deliveryFeeCalculatingService.calculateDeliveryFee(station, vehicle));
        } catch (VehicleUsageForbiddenError e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usage of selected vehicle type is forbidden");
        }
    }
}
