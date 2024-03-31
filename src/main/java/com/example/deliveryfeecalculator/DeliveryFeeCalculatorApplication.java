package com.example.deliveryfeecalculator;

import com.example.deliveryfeecalculator.model.Station;
import com.example.deliveryfeecalculator.model.Vehicle;
import com.example.deliveryfeecalculator.repository.StationRepository;
import com.example.deliveryfeecalculator.service.DeliveryFeeCalculatingService;
import com.example.deliveryfeecalculator.service.WeatherDataImportingService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DeliveryFeeCalculatorApplication {

	public static void main(String[] args) {
		ApplicationContext applicationContext = SpringApplication.run(DeliveryFeeCalculatorApplication.class, args);


		/*WeatherDataImportingService weatherDataImportingService = applicationContext.getBean(WeatherDataImportingService.class);
		weatherDataImportingService.importWeatherData();

		StationRepository stationRepository = applicationContext.getBean(StationRepository.class);

		DeliveryFeeCalculatingService deliveryFeeCalculatingService = applicationContext.getBean(DeliveryFeeCalculatingService.class);
*/
	}





}
