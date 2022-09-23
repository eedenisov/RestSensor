package eedenisov.project3.RestSensorApp.client;

import eedenisov.project3.RestSensorApp.dto.MeasurementDTO;
import eedenisov.project3.RestSensorApp.dto.MeasurementResponse;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.*;


/**
 * @author eedenisov
 */
public class Client {
    public static void main(String[] args) {
        Random random = new Random();
        double minTemp = -30.00;
        double maxTemp = 45.00;

        String sensorName = "Sensor-123";

        regSensor(sensorName);

        for (int i = 0; i < 100; i++) {
            sendMeasurement(Math.round(random.nextDouble() * (maxTemp - minTemp)) - maxTemp,
                    random.nextBoolean(),
                    sensorName);
        }

        List<MeasurementDTO> list = getMeasurement();
        for (var l : list) {
            System.out.println(l);
        }

        String rainingDays = countRainingDays();
        System.out.println(rainingDays);
    }

    private static String countRainingDays() {
        final String url = "http://localhost:8080/measurements/rainingDays";
        RestTemplate restTemplate = new RestTemplate();

        return restTemplate.getForObject(url, String.class);
    }

    private static List<MeasurementDTO> getMeasurement() {
        final String urlMeasurements = "http://localhost:8080/measurements";
        RestTemplate restTemplate = new RestTemplate();

        MeasurementResponse jsonResponse = restTemplate.getForObject(
                urlMeasurements, MeasurementResponse.class);

        if (jsonResponse == null || jsonResponse.getMeasurements() == null) {
            return Collections.emptyList();
        }

        return new ArrayList<>(jsonResponse.getMeasurements());
    }

    private static void sendMeasurement(double value, boolean raining, String sensorName) {
        final String urlAddMeasurements = "http://localhost:8088/measurements/add";

        Map<String, Object> jsonData = new HashMap<>();
        jsonData.put("value", value);
        jsonData.put("raining", raining);
        jsonData.put("sensor", Map.of("name", sensorName));

        postRequest(urlAddMeasurements, jsonData);
    }

    private static void regSensor(String sensorName) {
        final String urlRegSensor = "http://localhost:8088/sensors/registration";

        Map<String, Object> jsonData = new HashMap<>();
        jsonData.put("name", sensorName);

        postRequest(urlRegSensor, jsonData);

    }

    private static void postRequest(String urlRegSensor, Map<String, Object> jsonData) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Object> request = new HttpEntity<>(jsonData, headers);

        try {
            restTemplate.postForObject(urlRegSensor, request, String.class);
            System.out.println("Измерение успешно отправлено!");
        } catch (HttpClientErrorException e) {
            e.fillInStackTrace();
        }
    }
}
