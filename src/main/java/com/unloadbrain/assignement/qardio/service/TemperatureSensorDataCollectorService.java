package com.unloadbrain.assignement.qardio.service;

import com.unloadbrain.assignement.qardio.dto.message.TemperatureSensorDataMessage;
import com.unloadbrain.assignement.qardio.dto.reqeust.TemperatureSensorDataRequest;
import com.unloadbrain.assignement.qardio.dto.response.DataCollectionStatusResponse;
import com.unloadbrain.assignement.qardio.exception.KafkaRecordProducerException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

/**
 * Collect temperature sensor data and put it to Apache Kafka record.
 */
@Slf4j
@Service
public class TemperatureSensorDataCollectorService implements SensorDataCollectorService<TemperatureSensorDataRequest> {

    private final LoggedInUserService loggedInUserService;
    private final KafkaTemplate kafkaTemplate;
    private final String topic;

    public TemperatureSensorDataCollectorService(LoggedInUserService loggedInUserService,
                                                 KafkaTemplate kafkaTemplate,
                                                 @Value("${app.kafka.topic.temperature-sensor}") String topic) {
        this.loggedInUserService = loggedInUserService;
        this.kafkaTemplate = kafkaTemplate;
        this.topic = topic;
    }

    /**
     * Read temperature sensor data and put it to Apache Kafka record.
     *
     * @param temperatureSensorDataRequest temperature sensor data.
     */
    @Override
    public DataCollectionStatusResponse collect(TemperatureSensorDataRequest temperatureSensorDataRequest) {

        TemperatureSensorDataMessage message = TemperatureSensorDataMessage.builder()
                .deviceId(loggedInUserService.getLoggedInDeviceId())
                .temperatureInFahrenheit(temperatureSensorDataRequest.getTemperatureInFahrenheit())
                .unixTimestamp(temperatureSensorDataRequest.getUnixTimestamp())
                .build();

        ListenableFuture<SendResult<String, TemperatureSensorDataMessage>> future = kafkaTemplate.send(topic, message);
        future.addCallback(new ListenableFutureCallback<SendResult<String, TemperatureSensorDataMessage>>() {

            @Override
            public void onSuccess(SendResult<String, TemperatureSensorDataMessage> result) {
                log.info("Temperature sensor data successfully sent to Kafka.");
            }

            @Override
            public void onFailure(Throwable ex) {
                log.error("Temperature sensor data could not be sent to Kafka.", ex);
                throw new KafkaRecordProducerException("Temperature sensor data could not be sent to Kafka.");
            }
        });

        try {
            // Blocking call to return success status.
            future.get();
        } catch (InterruptedException | ExecutionException ex) {
            log.error("Apache Kafka ListenableFuture get() exception.", ex);
            return DataCollectionStatusResponse.builder().success(false).build();
        }

        return DataCollectionStatusResponse.builder().success(true).build();
    }

    /**
     * Read temperature sensor data in bulk and put it to Apache Kafka record.
     *
     * @param temperatureSensorDataRequests list of temperature sensor data.
     */
    @Override
    public List<DataCollectionStatusResponse> collect(List<TemperatureSensorDataRequest> temperatureSensorDataRequests) {
        return temperatureSensorDataRequests.stream()
                .map(request -> collect(request))
                .collect(Collectors.toList());
    }
}
