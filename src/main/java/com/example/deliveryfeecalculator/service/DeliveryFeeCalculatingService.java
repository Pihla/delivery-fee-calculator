package com.example.deliveryfeecalculator.service;
import com.example.deliveryfeecalculator.VehicleUsageForbiddenError;
import com.example.deliveryfeecalculator.model.WeatherPhenomenon;
import com.example.deliveryfeecalculator.model.Station;
import com.example.deliveryfeecalculator.model.Vehicle;
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

    public double calculateDeliveryFee(Station station, Vehicle vehicleType) throws VehicleUsageForbiddenError {
        WeatherObservation weatherObservation = findCurrentWeather(station);
        double deliveryFee = 0;

        //calculate regional base fee
        double rbf = regionalBaseFeeRepository.findRegionalBaseFeeByStationAndVehicle(station, vehicleType).getRbf();
        deliveryFee += rbf;


        //calculate air temperature fee
        double atef = 0;
        if(vehicleType == Vehicle.SCOOTER || vehicleType == Vehicle.BIKE) {
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
        if(vehicleType == Vehicle.BIKE) {
            if(weatherObservation.getWindSpeed() > 20) {
                throw new VehicleUsageForbiddenError();
            }
            else if(weatherObservation.getWindSpeed() >= 10) {
                wsef = 0.5;
            }
        }
        deliveryFee += wsef;

        //calculate weather phenomenon extra fee
        double wpef = 0;
        String weatherPhenomenon = String.valueOf(weatherObservation.getPhenomenon());
        if(vehicleType == Vehicle.SCOOTER || vehicleType == Vehicle.BIKE) {
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
                throw new VehicleUsageForbiddenError();
            }
        }
        deliveryFee += wpef;

        return deliveryFee;
    }


    private WeatherObservation findCurrentWeather(Station station) {
        return weatherObservationRepository.findTop1ByStationOrderByTimestampDesc(station);
    }


}
