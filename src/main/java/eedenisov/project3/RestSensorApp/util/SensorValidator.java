package eedenisov.project3.RestSensorApp.util;

import eedenisov.project3.RestSensorApp.models.Sensor;
import eedenisov.project3.RestSensorApp.services.SensorsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


/**
 * @author eedenisov
 */
@Component
public class SensorValidator implements Validator {
    private final SensorsService sensorsService;

    @Autowired
    public SensorValidator(SensorsService sensorsService) {
        this.sensorsService = sensorsService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Sensor.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Sensor sensor = (Sensor) target;

        if (sensorsService.checkSensorByName(sensor.getName()).isPresent()) {
            errors.rejectValue("name", "", "Уже есть сенсор с таким именем");
        }
    }
}
