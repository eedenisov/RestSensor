package eedenisov.project3.RestSensorApp.controllers;

import eedenisov.project3.RestSensorApp.dto.SensorDTO;
import eedenisov.project3.RestSensorApp.models.Sensor;
import eedenisov.project3.RestSensorApp.services.SensorsService;
import eedenisov.project3.RestSensorApp.util.ErrorResponse;
import eedenisov.project3.RestSensorApp.util.SensorException;
import eedenisov.project3.RestSensorApp.util.SensorValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


/**
 * @author eedenisov
 */
@RestController
@RequestMapping("/sensors")
public class SensorsController {

    private final SensorsService sensorsService;
    private final SensorValidator sensorValidator;
    private final ModelMapper modelMapper;

    @Autowired
    public SensorsController(SensorsService sensorsService, SensorValidator sensorValidator, ModelMapper modelMapper) {
        this.sensorsService = sensorsService;
        this.sensorValidator = sensorValidator;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/registration")
    public ResponseEntity<HttpStatus> registrationSensor(@RequestBody @Valid SensorDTO sensorDTO,
                                                         BindingResult bindingResult) throws SensorException {
        Sensor sensorToReg = convertToSensor(sensorDTO);

        sensorValidator.validate(sensorToReg, bindingResult);
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
            throw new SensorException(errMsg.toString());
        }

        sensorsService.saveSensor(sensorToReg);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handlerException(SensorException e) {
        ErrorResponse response = new ErrorResponse(
                e.getMessage(),
                System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    private Sensor convertToSensor(SensorDTO sensorDTO) {
        return modelMapper.map(sensorDTO, Sensor.class);
    }
}
