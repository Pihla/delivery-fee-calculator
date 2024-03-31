package com.example.deliveryfeecalculator.model;

import jakarta.persistence.*;

@Entity
public class Station {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    @Column(unique = true)
    private String cityName;
    @Column(unique = true)
    private String stationName;

    protected Station() {

    }

    /**
     * @return Name of the weather station
     */
    public String getStationName() {
        return stationName;
    }

    /**
     * @return String representation of the station, including its name and the city it belongs to.
     */
    @Override
    public String toString() {
        return String.format("station %s in city %s", stationName, cityName);
    }

    public Station(String cityName, String stationName) {
        this.cityName = cityName;
        this.stationName = stationName;
    }
}
