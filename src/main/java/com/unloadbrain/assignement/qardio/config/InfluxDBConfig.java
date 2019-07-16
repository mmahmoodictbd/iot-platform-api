package com.unloadbrain.assignement.qardio.config;

import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Pong;
import org.influxdb.dto.Query;
import org.influxdb.impl.InfluxDBResultMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * This class provides InfluxDB related configuration and beans.
 */
@Slf4j
@Configuration
public class InfluxDBConfig {

    private final String url;
    private final String username;
    private final String password;
    private final String database;
    private final String retentionPolicy;
    private final long readTimeout;

    public InfluxDBConfig(@Value("${app.influxdb.url}") String url,
                          @Value("${app.influxdb.user}") String username,
                          @Value("${app.influxdb.pass}") String password,
                          @Value("${app.influxdb.database}") String database,
                          @Value("${app.influxdb.retention-policy}") String retentionPolicy,
                          @Value("${app.influxdb.read-timeout-in-seconds}") long readTimeout) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.database = database;
        this.retentionPolicy = retentionPolicy;
        this.readTimeout = readTimeout;
    }

    @Bean
    public InfluxDB influxDB() {

        OkHttpClient.Builder builder = new OkHttpClient.Builder().readTimeout(readTimeout, TimeUnit.SECONDS);
        InfluxDB influxDB = InfluxDBFactory.connect(url, username, password, builder);

        influxDB.query(new Query("CREATE DATABASE " + database, database));
        influxDB.setDatabase(database);

        influxDB.setRetentionPolicy(retentionPolicy);
        influxDB.setLogLevel(InfluxDB.LogLevel.BASIC);

        Pong response = influxDB.ping();
        if ("unknown".equalsIgnoreCase(response.getVersion())) {
            log.error("Error pinging InfluxDB server.");
        }

        return influxDB;
    }

    @Bean
    public InfluxDBResultMapper influxDBResultMapper() {
        return new InfluxDBResultMapper();
    }

}
