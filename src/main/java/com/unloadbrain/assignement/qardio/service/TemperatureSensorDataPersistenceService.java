package com.unloadbrain.assignement.qardio.service;

import com.unloadbrain.assignement.qardio.dto.message.TemperatureSensorDataMessage;
import lombok.extern.slf4j.Slf4j;
import org.influxdb.InfluxDB;
import org.influxdb.dto.Point;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * Listen to TrackTemperature Apache Kafka topic and persist to InfluxDB.
 */
@Slf4j
@Service
public class TemperatureSensorDataPersistenceService {

    private InfluxDB influxDB;

    public TemperatureSensorDataPersistenceService(InfluxDB influxDB) {
        this.influxDB = influxDB;
    }

    /**
     * Persist temperature data to InfluxDB.
     *
     * @param message the Apache Kafka message
     */
    @KafkaListener(topics = "TrackTemperature", groupId = "iot")
    public void trackTemperatureMessageListener(TemperatureSensorDataMessage message) {

        log.info("Received message {} TrackTemperature topic from group 'iot'", message);

        Point point = Point.measurement("TemperatureSensor")
                .time(message.getUnixTimestamp() * 1000L, TimeUnit.MILLISECONDS)
                .addField("deviceId", message.getDeviceId())
                .addField("temperatureInFahrenheit", message.getTemperatureInFahrenheit())
                .build();

        influxDB.write(point);
    }


}
