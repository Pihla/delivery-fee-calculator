package com.example.deliveryfeecalculator;

import com.example.deliveryfeecalculator.model.RegionalBaseFee;
import com.example.deliveryfeecalculator.model.Station;
import com.example.deliveryfeecalculator.model.Vehicle;
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

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        //generate stations
        Station tallinnStation = new Station("Tallinn", "Tallinn-Harku");
        Station tartuStation = new Station("Tartu", "Tartu-Tõravere");
        Station parnuStation = new Station("Pärnu", "Pärnu");

        //save stations to db
        stationRepository.save(tallinnStation);
        stationRepository.save(tartuStation);
        stationRepository.save(parnuStation);

        //save regional base fees to db
        regionalBaseFeeRepository.save(new RegionalBaseFee(tallinnStation, Vehicle.CAR, 4.0));
        regionalBaseFeeRepository.save(new RegionalBaseFee(tallinnStation, Vehicle.SCOOTER, 3.5));
        regionalBaseFeeRepository.save(new RegionalBaseFee(tallinnStation, Vehicle.BIKE, 3.0));

        regionalBaseFeeRepository.save(new RegionalBaseFee(tartuStation, Vehicle.CAR, 3.5));
        regionalBaseFeeRepository.save(new RegionalBaseFee(tartuStation, Vehicle.SCOOTER, 3.0));
        regionalBaseFeeRepository.save(new RegionalBaseFee(tartuStation, Vehicle.BIKE, 2.5));

        regionalBaseFeeRepository.save(new RegionalBaseFee(parnuStation, Vehicle.CAR, 3.0));
        regionalBaseFeeRepository.save(new RegionalBaseFee(parnuStation, Vehicle.SCOOTER, 2.5));
        regionalBaseFeeRepository.save(new RegionalBaseFee(parnuStation, Vehicle.BIKE, 2.0));

    }
}