package com.unloadbrain.assignement.qardio;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.unloadbrain.assignement.qardio.dto.reqeust.TemperatureSensorDataRequest;
import com.unloadbrain.assignement.qardio.dto.response.DataCollectionStatusResponse;
import com.unloadbrain.assignement.qardio.service.SensorDataCollectorService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class})
@AutoConfigureMockMvc
@ActiveProfiles("it")
public class TemperatureCollectorApiIT {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private SensorDataCollectorService sensorDataCollectorServiceMock;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void shouldCreateTemperatureDataPointAndReturnHttp201AndSuccessStatus() throws Exception {

        when(sensorDataCollectorServiceMock.collect(any(TemperatureSensorDataRequest.class)))
                .thenReturn(DataCollectionStatusResponse.builder().success(true).build());

        TemperatureSensorDataRequest dataRequest =
                TemperatureSensorDataRequest.builder().temperatureInFahrenheit(20).build();

        mockMvc.perform(post("/temperatures")
                .content(objectMapper.writeValueAsString((dataRequest)))
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.success").value("true"))
                .andDo(print());
    }

    @Test
    public void shouldCreateTemperatureDataPointsAndReturnHttp207AndSuccessStatuses() throws Exception {

        List<DataCollectionStatusResponse> responses = new ArrayList<>();
        responses.add(DataCollectionStatusResponse.builder().success(true).build());
        responses.add(DataCollectionStatusResponse.builder().success(false).build());
        when(sensorDataCollectorServiceMock.collect(any(List.class))).thenReturn(responses);

        List<TemperatureSensorDataRequest> requests = new ArrayList<>();
        requests.add(TemperatureSensorDataRequest.builder().temperatureInFahrenheit(20).build());
        requests.add(TemperatureSensorDataRequest.builder().temperatureInFahrenheit(21).build());

        mockMvc.perform(post("/batch/temperatures")
                .content(objectMapper.writeValueAsString((requests)))
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isMultiStatus())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$[0].success").value("true"))
                .andExpect(jsonPath("$[1].success").value("false"))
                .andDo(print());
    }
}