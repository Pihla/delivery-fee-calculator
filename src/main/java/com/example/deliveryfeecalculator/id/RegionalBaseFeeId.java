package com.example.deliveryfeecalculator.id;

import com.example.deliveryfeecalculator.model.Station;
import com.example.deliveryfeecalculator.model.VehicleType;

import java.io.Serializable;

public class RegionalBaseFeeId implements Serializable {
    private Station station;
    private VehicleType vehicleType;
}
