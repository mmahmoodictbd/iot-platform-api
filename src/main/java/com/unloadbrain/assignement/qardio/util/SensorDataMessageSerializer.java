package com.unloadbrain.assignement.qardio.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

/**
 * Abstract sensor data Apache Kafka message serializer class.
 */
@Slf4j
public abstract class SensorDataMessageSerializer<T> implements Serializer<T> {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure(Map map, boolean b) {
    }

    @Override
    public byte[] serialize(String topic, T obj) {

        byte[] retVal = null;

        try {
            retVal = objectMapper.writeValueAsString(obj).getBytes();
        } catch (Exception ex) {
            log.error("Could not serialize object {}", obj, ex);
        }

        return retVal;
    }

    @Override
    public void close() {
    }
}
