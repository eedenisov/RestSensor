package eedenisov.project3.RestSensorApp.services;

import eedenisov.project3.RestSensorApp.models.Sensor;
import eedenisov.project3.RestSensorApp.repositories.SensorsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


/**
 * @author eedenisov
 */
@Service
@Transactional(readOnly = true)
public class SensorsService {

    private final SensorsRepository sensorsRepository;

    @Autowired
    public SensorsService(SensorsRepository sensorsRepository) {
        this.sensorsRepository = sensorsRepository;
    }

    @Transactional
    public void saveSensor(Sensor sensor) {
        sensorsRepository.save(sensor);
    }

    public Optional<Sensor> checkSensorByName(String name) {
        return sensorsRepository.findByName(name);
    }
}
