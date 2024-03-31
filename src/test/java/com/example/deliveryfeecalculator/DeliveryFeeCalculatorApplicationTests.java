package com.example.deliveryfeecalculator;

import com.example.deliveryfeecalculator.repository.RegionalBaseFeeRepository;
import com.example.deliveryfeecalculator.repository.WeatherObservationRepository;
import com.example.deliveryfeecalculator.service.DeliveryFeeCalculatingService;
import com.example.deliveryfeecalculator.service.WeatherDataImportingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class DeliveryFeeCalculatorApplicationTests {
	@Autowired
	WeatherDataImportingService weatherDataImportingService;

	@Autowired
	WeatherObservationRepository weatherObservationRepository;

	@Autowired
	DeliveryFeeCalculatingService deliveryFeeCalculatingService;

	@Autowired
	RegionalBaseFeeRepository regionalBaseFeeRepository;

	@Test
	void contextLoads() {
	}

	@Test
	void correctNumberOfWeatherObservations() throws ParserConfigurationException, URISyntaxException, IOException, SAXException {
		weatherDataImportingService.importWeatherData();
		weatherDataImportingService.importWeatherData();
		long numberOfObservations = weatherObservationRepository.count();
		assertEquals(6L, numberOfObservations);
	}


}
