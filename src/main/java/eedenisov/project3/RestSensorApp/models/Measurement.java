package eedenisov.project3.RestSensorApp.models;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;


/**
 * @author eedenisov
 */
@Entity
@Table(name = "Measurement")
public class Measurement {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "value")
    @NotNull
    @Min(value = -100, message = "Значение должно быть больше -100")
    @Max(value = 100, message = "Значение должно быть меньше 100")
    private Double value;

    @Column(name = "raining")
    @NotNull
    private Boolean raining;

    @Column(name = "data_time")
    @NotNull
    private LocalDateTime dataTime;

    @ManyToOne
    @JoinColumn(name = "sensor", referencedColumnName = "name")
    @NotNull
    private Sensor sensor;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public LocalDateTime getDataTime() {
        return dataTime;
    }
    public void setDataTime(LocalDateTime dataTime) {
        this.dataTime = dataTime;
    }

    public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }
}
