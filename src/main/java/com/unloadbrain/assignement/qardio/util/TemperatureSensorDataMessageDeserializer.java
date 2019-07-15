package com.unloadbrain.assignement.qardio.util;

import com.unloadbrain.assignement.qardio.dto.message.TemperatureSensorDataMessage;

/**
 * Temperature sensor data Apache Kafka message deserializer class.
 */
public class TemperatureSensorDataMessageDeserializer extends SensorDataMessageDeserializer<TemperatureSensorDataMessage> {

    public TemperatureSensorDataMessageDeserializer() {
        super(TemperatureSensorDataMessage.class);
    }

}
