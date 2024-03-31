package com.example.deliveryfeecalculator.controller;

import com.example.deliveryfeecalculator.exception.VehicleUsageForbiddenException;
import com.example.deliveryfeecalculator.model.Station;
import com.example.deliveryfeecalculator.model.VehicleType;
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

    /**
     * @param city One of the following: Tallinn, Tartu, Pärnu
     * @param vehicleType One of the following: Car, Scooter, Bike
     * @return Calculated delivery fee in euros based on current weather data in specified city and chosen vehicle type.
     *  Returns error code 400 - bad request when city name or vehicle type is not specified or isn't one of the
     *  given possible values or when vehicle type is not allowed with current weather conditions. If city name and
     *  vehicle type are valid, but an exception occurs in the program, error code 500 is returned.
     */
    @GetMapping()
    ResponseEntity<?> deliveryFee(String city, String vehicleType) {
        if(city == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("City name not specified");
        }

        if(vehicleType == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Vehicle type not specified");
        }

        Station station = stationRepository.findByCityNameIgnoreCase(city);

        if(station == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid city name");
        }

        VehicleType vehicle;
        try {
            vehicle = VehicleType.valueOf(vehicleType.toUpperCase());
        }catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid vehicle type");
        }

        try {
            return ResponseEntity.ok(deliveryFeeCalculatingService.calculateDeliveryFee(station, vehicle));
        } catch (VehicleUsageForbiddenException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usage of selected vehicle type is forbidden");
        }
    }
}
