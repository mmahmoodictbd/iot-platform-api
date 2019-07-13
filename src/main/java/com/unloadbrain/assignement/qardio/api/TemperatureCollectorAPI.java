package com.unloadbrain.assignement.qardio.api;

import com.unloadbrain.assignement.qardio.dto.reqeust.TemperatureSensorDataRequest;
import com.unloadbrain.assignement.qardio.service.SensorDataCollectorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * This class provide API endpoint to collect temperature data from sensors.
 */
@Slf4j
@RestController
public class TemperatureCollectorAPI {

    private final SensorDataCollectorService temperatureSensorDataCollectorService;

    /**
     * Instantiates a new Temperature collector api.
     *
     * @param temperatureSensorDataCollectorService the temperature sensor data collector service
     */
    public TemperatureCollectorAPI(SensorDataCollectorService temperatureSensorDataCollectorService) {
        this.temperatureSensorDataCollectorService = temperatureSensorDataCollectorService;
    }


    /**
     * Collect temperature from sensor.
     *
     * @param dataRequest the temperature data request.
     * @return the response entity with HTTP Status 201.
     */
    @PostMapping("/temperature")
    public ResponseEntity<Void> collectTemperature(TemperatureSensorDataRequest dataRequest) {
        temperatureSensorDataCollectorService.collect(dataRequest);
        return new ResponseEntity(HttpStatus.CREATED);
    }
}
