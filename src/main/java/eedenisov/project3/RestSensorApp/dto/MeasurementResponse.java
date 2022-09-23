package eedenisov.project3.RestSensorApp.dto;

import java.util.List;


/**
 * @author eedenisov
 */
public class MeasurementResponse {
    private List<MeasurementDTO> measurements;

    public MeasurementResponse(List<MeasurementDTO> measurements) {
        this.measurements = measurements;
    }

    public MeasurementResponse() {
    }

    public List<MeasurementDTO> getMeasurements() {
        return measurements;
    }

    public void setMeasurements(List<MeasurementDTO> measurements) {
        this.measurements = measurements;
    }

    @Override
    public String toString() {
        return "MeasurementResponse{" +
                "measurements=" + measurements +
                '}';
    }
}
