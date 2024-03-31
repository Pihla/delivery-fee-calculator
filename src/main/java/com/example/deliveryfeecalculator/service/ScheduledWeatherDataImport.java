package com.example.deliveryfeecalculator.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Date;

@Component
public class ScheduledWeatherDataImport {
    private final Logger logger = LoggerFactory.getLogger(ScheduledWeatherDataImport.class);
    private WeatherDataImportingService weatherDataImportingService;

    ScheduledWeatherDataImport(WeatherDataImportingService weatherDataImportingService) {
        this.weatherDataImportingService = weatherDataImportingService;
    }

    @Scheduled(cron = "${cron.expression: 0 15 * * * *}")
    public void execute() throws ParserConfigurationException, URISyntaxException, IOException, SAXException {
        logger.info(String.format("Scheduled task executed at %s", new Date()));
        weatherDataImportingService.importWeatherData();
    }
}
