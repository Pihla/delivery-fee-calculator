package com.example.deliveryfeecalculator.service;

import com.example.deliveryfeecalculator.exception.VehicleUsageForbiddenException;
import com.example.deliveryfeecalculator.model.WeatherPhenomenon;
import com.example.deliveryfeecalculator.model.Station;
import com.example.deliveryfeecalculator.model.VehicleType;
import com.example.deliveryfeecalculator.model.WeatherObservation;
import com.example.deliveryfeecalculator.repository.RegionalBaseFeeRepository;
import com.example.deliveryfeecalculator.repository.StationRepository;
import com.example.deliveryfeecalculator.repository.WeatherObservationRepository;
import org.springframework.stereotype.Service;

@Service
public class DeliveryFeeCalculatingService {
    WeatherObservationRepository weatherObservationRepository;
    StationRepository stationRepository;
    RegionalBaseFeeRepository regionalBaseFeeRepository;

    DeliveryFeeCalculatingService(WeatherObservationRepository weatherObservationRepository, StationRepository stationRepository, RegionalBaseFeeRepository regionalBaseFeeRepository) {
        this.weatherObservationRepository = weatherObservationRepository;
        this.stationRepository = stationRepository;
        this.regionalBaseFeeRepository = regionalBaseFeeRepository;
    }

    /**
     * @param station Weather station.
     * @param vehicleType Enum representing the vehicle type.
     * @return Delivery fee based on the weather station and vehicle type. Calculated by taking regional base fee (RBF)
     *  and adding extra fees when necessary. Extra fees are: air temperature extra fee (ATEF), wind speed extra fee (WSEF)
     *  and weather phenomenon extra fee (WPEF).
     * @throws VehicleUsageForbiddenException When weather conditions are too harsh to do delivery with given vehicle type.
     */
    public double calculateDeliveryFee(Station station, VehicleType vehicleType) throws VehicleUsageForbiddenException {
        WeatherObservation weatherObservation = findCurrentWeather(station);
        double deliveryFee = 0;

        //calculate regional base fee
        double rbf = regionalBaseFeeRepository.findRegionalBaseFeeByStationAndVehicleType(station, vehicleType).getRegionalBaseFee();
        deliveryFee += rbf;


        //calculate air temperature fee
        double atef = 0;
        if(vehicleType == VehicleType.SCOOTER || vehicleType == VehicleType.BIKE) {
            if(weatherObservation.getAirTemperature() < -10) {
                atef = 1;
            }
            else if(weatherObservation.getAirTemperature() <= 0) {
                atef = 0.5;
            }
        }
        deliveryFee += atef;

        //calculate wind speed extra fee
        double wsef = 0;
        if(vehicleType == VehicleType.BIKE) {
            if(weatherObservation.getWindSpeed() > 20) {
                throw new VehicleUsageForbiddenException();
            }
            else if(weatherObservation.getWindSpeed() >= 10) {
                wsef = 0.5;
            }
        }
        deliveryFee += wsef;

        //calculate weather phenomenon extra fee
        double wpef = 0;
        String weatherPhenomenon = String.valueOf(weatherObservation.getPhenomenon());
        if(vehicleType == VehicleType.SCOOTER || vehicleType == VehicleType.BIKE) {
            if(weatherPhenomenon.contains("snow") || weatherPhenomenon.contains("sleet")) {
                wpef = 1;
            }
            else if(weatherPhenomenon.contains("rain")) {
                wpef = 0.5;
            }
            else if(weatherObservation.getPhenomenon() == WeatherPhenomenon.GLAZE ||
                    weatherObservation.getPhenomenon() == WeatherPhenomenon.HAIL ||
                    weatherObservation.getPhenomenon() == WeatherPhenomenon.THUNDER ||
                    weatherObservation.getPhenomenon() == WeatherPhenomenon.THUNDERSTORM) {
                throw new VehicleUsageForbiddenException();
            }
        }
        deliveryFee += wpef;

        return deliveryFee;
    }


    private WeatherObservation findCurrentWeather(Station station) {
        return weatherObservationRepository.findTop1ByStationOrderByTimestampDesc(station);
    }


}
