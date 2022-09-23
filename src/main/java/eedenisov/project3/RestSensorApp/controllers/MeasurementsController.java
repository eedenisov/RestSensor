package eedenisov.project3.RestSensorApp.controllers;

import eedenisov.project3.RestSensorApp.dto.MeasurementDTO;
import eedenisov.project3.RestSensorApp.dto.MeasurementResponse;
import eedenisov.project3.RestSensorApp.models.Measurement;
import eedenisov.project3.RestSensorApp.services.MeasurementsService;
import eedenisov.project3.RestSensorApp.util.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @author eedenisov
 */
@RestController
@RequestMapping("/measurements")
public class MeasurementsController {
    private final MeasurementsService measurementsService;
    private final MeasurementValidator measurementValidator;
    private final ModelMapper modelMapper;

    @Autowired
    public MeasurementsController(MeasurementsService measurementsService,
                                  MeasurementValidator measurementValidator,
                                  ModelMapper modelMapper) {
        this.measurementsService = measurementsService;
        this.measurementValidator = measurementValidator;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> addMeasurement(@RequestBody @Valid MeasurementDTO measurementDTO,
                                                     BindingResult bindingResult) throws MeasurementException {
        Measurement measurementToAdd = convertToMeasurement(measurementDTO);

        measurementValidator.validate(measurementToAdd, bindingResult);

        if (bindingResult.hasErrors()) {
            StringBuilder errMsg = new StringBuilder();

            List<FieldError> errors = bindingResult.getFieldErrors();
            for (var error : errors) {
                errMsg
                        .append(error.getField())
                        .append(" - ")
                        .append(error.getDefaultMessage())
                        .append(";");
            }
            throw new MeasurementException(errMsg.toString());
        }
        measurementsService.saveMeasurement(measurementToAdd);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping()
    public MeasurementResponse getMeasurements() {
        return new MeasurementResponse(measurementsService.findAll().stream().map(this::convertToMeasurementDTO)
                .collect(Collectors.toList()));
    }

    @GetMapping("/rainingDays")
    public String getRainingDaysCount() {
        return "Raining days: " + measurementsService.findAll().stream().filter(Measurement::getRaining).count();
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handlerException(MeasurementException e) {
        ErrorResponse response = new ErrorResponse(
                e.getMessage(),
                System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    private Measurement convertToMeasurement(MeasurementDTO measurementDTO) {
        return modelMapper.map(measurementDTO, Measurement.class);
    }

    private MeasurementDTO convertToMeasurementDTO(Measurement measurement) {
        return modelMapper.map(measurement, MeasurementDTO.class);
    }
}
