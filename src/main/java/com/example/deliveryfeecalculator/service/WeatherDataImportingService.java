package com.example.deliveryfeecalculator.service;

import com.example.deliveryfeecalculator.model.Station;
import com.example.deliveryfeecalculator.model.WeatherObservation;
import com.example.deliveryfeecalculator.repository.StationRepository;
import com.example.deliveryfeecalculator.repository.WeatherObservationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;
import org.w3c.dom.*;

@Service
public class WeatherDataImportingService {
    Logger logger = LoggerFactory.getLogger(WeatherDataImportingService.class);
    WeatherObservationRepository weatherObservationRepository;
    StationRepository stationRepository;

    WeatherDataImportingService(WeatherObservationRepository weatherObservationRepository, StationRepository stationRepository) {
        this.weatherObservationRepository = weatherObservationRepository;
        this.stationRepository = stationRepository;
    }

    /**
     * Imports <a href="https://www.ilmateenistus.ee/ilma_andmed/xml/observations.php">weather data</a> and creates
     * WeatherObservation objects in the database.
     */
    public void importWeatherData() throws ParserConfigurationException, URISyntaxException, IOException, SAXException {
        logger.info(String.format("Importing weather data at %s", new Date()));

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = dbf.newDocumentBuilder();

        URL url = new URI("https://www.ilmateenistus.ee/ilma_andmed/xml/observations.php").toURL();
        InputStream stream = url.openStream();
        Document doc = docBuilder.parse(stream);

        Element observations = doc.getDocumentElement();
        String timestamp = observations.getAttribute("timestamp");
        NodeList stations = observations.getElementsByTagName("station");

        Map<String, Station> stationNamesAndStations = new HashMap<>();
        for (Station station : stationRepository.findAll()) {
            stationNamesAndStations.put(station.getStationName(), station);
        }

        for (int i = 0; i < stations.getLength(); i++) {
            String stationName = doc.getElementsByTagName("name").item(i).getTextContent();
            if(stationNamesAndStations.containsKey(stationName)) {
                String wmoCode = doc.getElementsByTagName("wmocode").item(i).getTextContent();
                String airTemperature = doc.getElementsByTagName("airtemperature").item(i).getTextContent();
                String windSpeed = doc.getElementsByTagName("windspeed").item(i).getTextContent();
                String weatherPhenomenon = doc.getElementsByTagName("phenomenon").item(i).getTextContent();

                WeatherObservation observation = new WeatherObservation(stationNamesAndStations.get(stationName), wmoCode, airTemperature, windSpeed, weatherPhenomenon, timestamp);
                weatherObservationRepository.save(observation);
                logger.info(String.format("Saved %s", observation));
            }
        }


    }
}
