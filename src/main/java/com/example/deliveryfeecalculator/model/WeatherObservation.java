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
        this.station = station;
        this.wmoCode = wmoCode;
        this.airTemperature = Double.parseDouble(airTemperature);
        this.windSpeed = Double.parseDouble(windSpeed);
        this.weatherPhenomenon = phenomenon_string.isEmpty() ? null : stringToPhenomenon(phenomenon_string);
        this.timestamp = Long.valueOf(timestamp);
    }

    /**
     * @return String representation of weather observation, including station, WMO code, air temperature, wind speed
     * and weather phenomenon.
     */
    @Override
    public String toString() {
       return String.format("Observation with values: %s, wmo code: %s, air temperature: %s, wind speed: %s, " +
                "weather phenomenon: %s", station, wmoCode, airTemperature, windSpeed, weatherPhenomenon);
    }

    protected WeatherObservation() {

    }

    private WeatherPhenomenon stringToPhenomenon(String string) {
        return WeatherPhenomenon.valueOf(string.toUpperCase().replace(" ", "_"));
    }

    /**
     * @return Air temperature in °C.
     */
    public double getAirTemperature() {
        return airTemperature;
    }

    /**
     * @return Average wind speed in m/s (meters per second).
     */
    public double getWindSpeed() {
        return windSpeed;
    }

    /**
     * @return Enum representing the weather phenomenon.
     */
    public WeatherPhenomenon getPhenomenon() {
        return weatherPhenomenon;
    }


}
