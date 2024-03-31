package com.example.deliveryfeecalculator.model;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class WeatherObservation {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private String name;
    private String wmoCode;
    private double airTemperature;
    private double windSpeed;

    private Phenomenon phenomenon;
    private Long timestamp;

    public WeatherObservation(String name, String wmoCode,String airTemperature, String windSpeed, String phenomenon_string, String timestamp) {
        //TODO exceptions
        this.name = name;
        this.wmoCode = wmoCode;
        this.airTemperature = Double.parseDouble(airTemperature);
        this.windSpeed = Double.parseDouble(windSpeed);
        this.phenomenon = phenomenon_string.isEmpty() ? null : stringToPhenomenon(phenomenon_string);
        this.timestamp = Long.valueOf(timestamp);
    }

    public String toString() {
       return String.format("Observation with values: station name: %s, wmo code: %s, air temperature: %s, wind speed: %s, " +
                "weather phenomenon: %s", name, wmoCode, airTemperature, windSpeed, phenomenon);
    }

    protected WeatherObservation() {

    }

    private Phenomenon stringToPhenomenon(String string) {
        return Phenomenon.valueOf(string.toUpperCase().replace(" ", "_"));
    }

    private enum Phenomenon {
        CLEAR, FEW_CLOUDS, VARIABLE_CLOUDS, CLOUDY_WITH_CLEAR_SPELLS, OVERCAST, LIGHT_SNOW_SHOWER, MODERATE_SNOW_SHOWER,
        HEAVY_SNOW_SHOWER, LIGHT_SHOWER, MODERATE_SHOWER, HEAVY_SHOWER, LIGHT_RAIN, MODERATE_RAIN, HEAVY_RAIN, GLAZE,
        LIGHT_SLEET, MODERATE_SLEET, LIGHT_SNOWFALL, MODERATE_SNOWFALL, HEAVY_SNOWFALL, BLOWING_SNOW, DRIFTING_SNOW,
        HAIL, MIST, FOG, THUNDER, THUNDERSTORM;
    }
}
