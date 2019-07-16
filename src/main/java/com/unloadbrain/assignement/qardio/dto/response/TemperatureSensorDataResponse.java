package com.unloadbrain.assignement.qardio.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO class to return temperature reporting data as HTTP response.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TemperatureSensorDataResponse {

    private String deviceId;
    private List<TemperatureSensorData> data;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TemperatureSensorData {

        private long unixTimestamp;
        private double temperatureInFahrenheit;
    }
}
