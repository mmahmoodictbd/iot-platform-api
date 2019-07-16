package com.unloadbrain.assignement.qardio.dto.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Message DTO class to put data to Apache Kafka.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TemperatureSensorDataMessage {

    private String deviceId;
    private double temperatureInFahrenheit;
    private long unixTimestamp;
}
