package com.unloadbrain.assignement.qardio;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.unloadbrain.assignement.qardio.dto.reqeust.TemperatureSensorDataRequest;
import com.unloadbrain.assignement.qardio.service.SensorDataCollectorService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class})
@AutoConfigureMockMvc
@ActiveProfiles("it")
public class TemperatureCollectorAPIContractIT {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SensorDataCollectorService sensorDataCollectorServiceMock;

    @Autowired
    private ObjectMapper objectMapper;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void shouldCreateTemperatureDataPointAndReturnHttp201() throws Exception {

        doNothing().when(sensorDataCollectorServiceMock).collect(any());

        TemperatureSensorDataRequest dataRequest =
                TemperatureSensorDataRequest.builder().temperatureInFahrenheit(20).build();

        mockMvc.perform(post("/temperature").content(objectMapper.writeValueAsString((dataRequest))))
                .andExpect(status().isCreated())
                .andDo(print());
    }
}