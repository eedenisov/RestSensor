package eedenisov.project3.RestSensorApp.services;

import eedenisov.project3.RestSensorApp.models.Measurement;
import eedenisov.project3.RestSensorApp.repositories.MeasurementsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;


/**
 * @author eedenisov
 */
@Service
@Transactional(readOnly = true)
public class MeasurementsService {
    private final MeasurementsRepository measurementsRepository;
    private final SensorsService sensorsService;

    @Autowired
    public MeasurementsService(MeasurementsRepository measurementsRepository, SensorsService sensorsService) {
        this.measurementsRepository = measurementsRepository;
        this.sensorsService = sensorsService;
    }

    public List<Measurement> findAll() {
        return measurementsRepository.findAll();
    }

    @Transactional
    public void saveMeasurement(Measurement measurement) {
        measurement.setSensor(sensorsService.checkSensorByName(measurement.getSensor().getName()).get());
        measurement.setDataTime(LocalDateTime.now());

        measurementsRepository.save(measurement);
    }
}
