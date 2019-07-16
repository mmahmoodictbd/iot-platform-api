package com.unloadbrain.assignement.qardio.api;

import com.unloadbrain.assignement.qardio.dto.response.TemperatureSensorDataResponse;
import com.unloadbrain.assignement.qardio.service.TemperatureSensorDataReportingService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * This class provide API endpoint to processed report of temperature sensor data.
 */
@RestController
public class TemperatureSensorDataReportingAPI {

    private final TemperatureSensorDataReportingService reportingService;

    public TemperatureSensorDataReportingAPI(TemperatureSensorDataReportingService reportingService) {
        this.reportingService = reportingService;
    }

    /**
     * Return temperature sensor data with start and end time limit.
     *
     * @param startTime the start time in unix timestamp.
     * @param endTime   the end time unix timestamp.
     * @return the temperature sensor data response DTO.
     */
    @GetMapping("/temperatures")
    public TemperatureSensorDataResponse getTemperatureData(@RequestParam long startTime, @RequestParam long endTime) {
        return reportingService.getReport(startTime, endTime);
    }
}
