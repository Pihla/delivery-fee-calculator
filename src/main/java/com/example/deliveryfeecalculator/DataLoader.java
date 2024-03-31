package com.example.deliveryfeecalculator;

import com.example.deliveryfeecalculator.model.RegionalBaseFee;
import com.example.deliveryfeecalculator.model.Station;
import com.example.deliveryfeecalculator.model.VehicleType;
import com.example.deliveryfeecalculator.repository.RegionalBaseFeeRepository;
import com.example.deliveryfeecalculator.repository.StationRepository;
import jakarta.transaction.Transactional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final StationRepository stationRepository;
    private final RegionalBaseFeeRepository regionalBaseFeeRepository;

    public DataLoader(StationRepository stationRepository, RegionalBaseFeeRepository regionalBaseFeeRepository) {
        this.stationRepository = stationRepository;
        this.regionalBaseFeeRepository = regionalBaseFeeRepository;
    }

    /**
     * Method initializes database with stations and regional base fees.
     * @param args Arguments.
     */
    @Override
    @Transactional
    public void run(String... args) {
        //generate stations
        Station tallinnStation = new Station("Tallinn", "Tallinn-Harku");
        Station tartuStation = new Station("Tartu", "Tartu-Tõravere");
        Station parnuStation = new Station("Pärnu", "Pärnu");

        //save stations to db
        stationRepository.save(tallinnStation);
        stationRepository.save(tartuStation);
        stationRepository.save(parnuStation);

        //save regional base fees to db
        regionalBaseFeeRepository.save(new RegionalBaseFee(tallinnStation, VehicleType.CAR, 4.0));
        regionalBaseFeeRepository.save(new RegionalBaseFee(tallinnStation, VehicleType.SCOOTER, 3.5));
        regionalBaseFeeRepository.save(new RegionalBaseFee(tallinnStation, VehicleType.BIKE, 3.0));

        regionalBaseFeeRepository.save(new RegionalBaseFee(tartuStation, VehicleType.CAR, 3.5));
        regionalBaseFeeRepository.save(new RegionalBaseFee(tartuStation, VehicleType.SCOOTER, 3.0));
        regionalBaseFeeRepository.save(new RegionalBaseFee(tartuStation, VehicleType.BIKE, 2.5));

        regionalBaseFeeRepository.save(new RegionalBaseFee(parnuStation, VehicleType.CAR, 3.0));
        regionalBaseFeeRepository.save(new RegionalBaseFee(parnuStation, VehicleType.SCOOTER, 2.5));
        regionalBaseFeeRepository.save(new RegionalBaseFee(parnuStation, VehicleType.BIKE, 2.0));

    }
}