package com.example.deliveryfeecalculator.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ScheduledWeatherDataImport {
    Logger logger = LoggerFactory.getLogger(ScheduledWeatherDataImport.class);
    private WeatherDataImportingService weatherDataImportingService;

    ScheduledWeatherDataImport(WeatherDataImportingService weatherDataImportingService) {
        this.weatherDataImportingService = weatherDataImportingService;
    }

    @Scheduled(cron = "0 * * * * *")  //TODO change default value and make it configurable
    public void execute() {
        logger.info(String.format("Scheduled task executed at %s", new Date()));
        weatherDataImportingService.importWeatherData();
    }
}
