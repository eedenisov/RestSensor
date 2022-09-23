package eedenisov.project3.RestSensorApp.util;

import eedenisov.project3.RestSensorApp.models.Measurement;
import eedenisov.project3.RestSensorApp.services.SensorsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


/**
 * @author eedenisov
 */
@Component
public class MeasurementValidator implements Validator {
    private final SensorsService sensorsService;

    @Autowired
    public MeasurementValidator(SensorsService sensorsService) {
        this.sensorsService = sensorsService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Measurement.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Measurement measurement = (Measurement) target;
        System.out.println(measurement.getSensor().getName());
        System.out.println(sensorsService.checkSensorByName(measurement.getSensor().getName()));

        if (measurement.getSensor() == null) {
            return;
        }
        if (sensorsService.checkSensorByName(measurement.getSensor().getName()).isEmpty()) {
            errors.rejectValue(
                    "sensor", "", "Зарегистрированного сенсора с таким именем нет");
        }
    }
}
