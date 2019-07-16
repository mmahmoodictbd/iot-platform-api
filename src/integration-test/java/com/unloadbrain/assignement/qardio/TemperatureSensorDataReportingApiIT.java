package com.unloadbrain.assignement.qardio;

import com.unloadbrain.assignement.qardio.dto.response.TemperatureSensorDataResponse;
import com.unloadbrain.assignement.qardio.service.TemperatureSensorDataReportingService;
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

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class})
@AutoConfigureMockMvc
@ActiveProfiles("it")
public class TemperatureSensorDataReportingApiIT {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TemperatureSensorDataReportingService temperatureSensorDataReportingServiceMock;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void shouldReturnTemperatureDataPoints() throws Exception {

        TemperatureSensorDataResponse.TemperatureSensorData data = TemperatureSensorDataResponse.TemperatureSensorData.builder()
                .temperatureInFahrenheit(100)
                .unixTimestamp(1563142796L)
                .build();
        List<TemperatureSensorDataResponse.TemperatureSensorData> dataList = Collections.singletonList(data);

        TemperatureSensorDataResponse response = TemperatureSensorDataResponse.builder()
                .deviceId("1234")
                .data(dataList)
                .build();

        when(temperatureSensorDataReportingServiceMock.getReport(anyLong(), anyLong())).thenReturn(response);

        mockMvc.perform(get("/temperatures")
                .param("startTime", "1563142796")
                .param("endTime", "1563142800"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.deviceId").value("1234"))
                .andExpect(jsonPath("$.data.[0].unixTimestamp").value("1563142796"))
                .andExpect(jsonPath("$.data.[0].temperatureInFahrenheit").value("100.0"))
                .andDo(print());
    }
}