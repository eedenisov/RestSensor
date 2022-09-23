package eedenisov.project3.RestSensorApp.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;


/**
 * @author eedenisov
 */
public class MeasurementDTO {

    @NotNull
    @Min(value = -100, message = "Значение должно быть больше -100")
    @Max(value = 100, message = "Значение должно быть меньше 100")
    private Double value;
    @NotNull
    private Boolean raining;

    @NotNull
    private SensorDTO sensor;

    public MeasurementDTO() {
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Boolean getRaining() {
        return raining;
    }

    public void setRaining(Boolean raining) {
        this.raining = raining;
    }

    public SensorDTO getSensor() {
        return sensor;
    }

    public void setSensor(SensorDTO sensor) {
        this.sensor = sensor;
    }

    @Override
    public String toString() {
        return "MeasurementDTO{" +
                "value=" + value +
                ", raining=" + raining +
                ", sensor=" + sensor +
                '}';
    }
}
