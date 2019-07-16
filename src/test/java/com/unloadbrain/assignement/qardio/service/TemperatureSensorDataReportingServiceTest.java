package com.unloadbrain.assignement.qardio.service;

import com.unloadbrain.assignement.qardio.domain.model.TemperatureSensorQueryResult;
import com.unloadbrain.assignement.qardio.domain.repository.TemperatureSensorDataRepository;
import com.unloadbrain.assignement.qardio.dto.response.TemperatureSensorDataResponse;
import org.junit.Before;
import org.junit.Test;

import java.time.Instant;
import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TemperatureSensorDataReportingServiceTest {

    private LoggedInUserService loggedInUserService;
    private TemperatureSensorDataRepository repository;

    private TemperatureSensorDataReportingService temperatureSensorDataReportingService;

    @Before
    public void setUp() throws Exception {
        this.loggedInUserService = mock(LoggedInUserService.class);
        this.repository = mock(TemperatureSensorDataRepository.class);
        this.temperatureSensorDataReportingService
                = new TemperatureSensorDataReportingService(loggedInUserService, repository);
    }

    @Test
    public void shouldReturnFilteredTemperatureSensorData() {

        // Given

        when(loggedInUserService.getLoggedInDeviceId()).thenReturn("1234");

        Instant instant = Instant.ofEpochSecond(1563142798);
        TemperatureSensorQueryResult queryResult
                = TemperatureSensorQueryResult.builder().time(instant).temperatureInFahrenheit(10.0).build();

        when(repository.getTemperatures(anyString(), anyLong(), anyLong()))
                .thenReturn(Collections.singletonList(queryResult));

        // When
        TemperatureSensorDataResponse response
                = temperatureSensorDataReportingService.getReport(1563142796, 1563142800);

        // Then

        assertEquals("1234", response.getDeviceId());
        assertEquals(10.0d, response.getData().get(0).getTemperatureInFahrenheit(), 1e-15);
        assertEquals(instant.getEpochSecond(), response.getData().get(0).getUnixTimestamp());
    }
}