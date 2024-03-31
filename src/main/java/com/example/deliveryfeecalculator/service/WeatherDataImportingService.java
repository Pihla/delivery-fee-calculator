package com.example.deliveryfeecalculator.service;
import com.example.deliveryfeecalculator.model.WeatherObservation;
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
import java.util.Arrays;
import java.util.Date;
import org.w3c.dom.*;
import java.util.HashSet;
import java.util.Set;

@Service
public class WeatherDataImportingService {
    Logger logger = LoggerFactory.getLogger(WeatherDataImportingService.class);
    WeatherObservationRepository weatherObservationRepository;

    WeatherDataImportingService(WeatherObservationRepository weatherObservationRepository) {
        this.weatherObservationRepository = weatherObservationRepository;
    }

    public void importWeatherData() {
        logger.info(String.format("Importing weather data at %s", new Date()));

        //TODO maybe do something else in the catch block
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = dbf.newDocumentBuilder();

            URL url = new URI("https://www.ilmateenistus.ee/ilma_andmed/xml/observations.php").toURL();
            InputStream stream = url.openStream();
            Document doc = docBuilder.parse(stream);

            Element observations = doc.getDocumentElement();
            String timestamp = observations.getAttribute("timestamp");
            NodeList stations = observations.getElementsByTagName("station");
            Set<String> stationsToLookFor = new HashSet<>(Arrays.asList("Tallinn-Harku", "Tartu-Tõravere", "Pärnu"));

            for (int i = 0; i < stations.getLength(); i++) {
                String stationName = doc.getElementsByTagName("name").item(i).getTextContent();
                if(stationsToLookFor.contains(stationName)) {
                    String wmoCode = doc.getElementsByTagName("wmocode").item(i).getTextContent();
                    String airTemperature = doc.getElementsByTagName("airtemperature").item(i).getTextContent();
                    String windSpeed = doc.getElementsByTagName("windspeed").item(i).getTextContent();
                    String weatherPhenomenon = doc.getElementsByTagName("phenomenon").item(i).getTextContent();

                    WeatherObservation observation = new WeatherObservation(stationName, wmoCode, airTemperature, windSpeed, weatherPhenomenon, timestamp);
                    weatherObservationRepository.save(observation);
                    logger.info(String.format("Saved %s", observation));
                }
            }
        } catch (ParserConfigurationException | IOException | SAXException | URISyntaxException e) {
            System.out.println(e.getMessage());
        }


    }
}
