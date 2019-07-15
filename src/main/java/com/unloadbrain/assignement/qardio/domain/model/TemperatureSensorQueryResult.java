package com.unloadbrain.assignement.qardio.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.influxdb.annotation.Column;
import org.influxdb.annotation.Measurement;

import java.time.Instant;

/**
 * Domain model class to read persisted data from InfluxDB.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Measurement(name = "TemperatureSensor", database = "sensordata")
public class TemperatureSensorQueryResult {

    @JsonIgnore
    @Column(name = "time")
    private Instant time;

    @Column(name = "temperatureInFahrenheit")
    private double temperatureInFahrenheit;
}