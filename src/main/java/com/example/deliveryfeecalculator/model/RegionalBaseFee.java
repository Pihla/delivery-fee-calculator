package com.example.deliveryfeecalculator.model;

import com.example.deliveryfeecalculator.id.RegionalBaseFeeId;
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
    private VehicleType vehicleType;
    private double rbf;

    public RegionalBaseFee(Station station, VehicleType vehicleType, double rbf) {
        this.station = station;
        this.vehicleType = vehicleType;
        this.rbf = rbf;
    }

    protected RegionalBaseFee() {

    }

    /**
     * @return Regional base fee in euros that is specified in the database for given vehicle type and city.
     */
    public double getRegionalBaseFee() {
        return rbf;
    }
}
