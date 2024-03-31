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

    public String getCityName() {
        return cityName;
    }

    public String getStationName() {
        return stationName;
    }

    public String toString() {
        return String.format("station %s in city %s", stationName, cityName);
    }

    public Station(String cityName, String stationName) {
        this.cityName = cityName;
        this.stationName = stationName;
    }
}
