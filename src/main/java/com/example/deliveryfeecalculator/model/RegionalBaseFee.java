package com.example.deliveryfeecalculator.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.ManyToOne;

@Entity
@IdClass(RegionalBaseFeeId.class)
public class RegionalBaseFee {
    @Id
    @ManyToOne
    private Station station;
    @Id
    private Vehicle vehicle;
    private double rbf;

    public RegionalBaseFee(Station station, Vehicle vehicle, double rbf) {
        this.station = station;
        this.vehicle = vehicle;
        this.rbf = rbf;
    }

    protected RegionalBaseFee() {

    }

    public double getRbf() {
        return rbf;
    }
}
