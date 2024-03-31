package com.example.deliveryfeecalculator.model;
import jakarta.persistence.*;

@Entity
public class WeatherObservation {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Station station;
    private String wmoCode;
    private double airTemperature;
    private double windSpeed;

    private WeatherPhenomenon weatherPhenomenon;
    private Long timestamp;

    public WeatherObservation(Station station, String wmoCode, String airTemperature, String windSpeed, String phenomenon_string, String timestamp) {
        //TODO exceptions
        this.station = station;
        this.wmoCode = wmoCode;
        this.airTemperature = Double.parseDouble(airTemperature);
        this.windSpeed = Double.parseDouble(windSpeed);
        this.weatherPhenomenon = phenomenon_string.isEmpty() ? null : stringToPhenomenon(phenomenon_string);
        this.timestamp = Long.valueOf(timestamp);
    }

    public String toString() {
       return String.format("Observation with values: %s, wmo code: %s, air temperature: %s, wind speed: %s, " +
                "weather phenomenon: %s", station, wmoCode, airTemperature, windSpeed, weatherPhenomenon);
    }

    protected WeatherObservation() {

    }

    private WeatherPhenomenon stringToPhenomenon(String string) {
        return WeatherPhenomenon.valueOf(string.toUpperCase().replace(" ", "_"));
    }

    public double getAirTemperature() {
        return airTemperature;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public WeatherPhenomenon getPhenomenon() {
        return weatherPhenomenon;
    }


}
