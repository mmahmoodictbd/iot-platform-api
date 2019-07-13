package com.unloadbrain.assignement.qardio.dto.reqeust;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TemperatureSensorDataRequest {

    private double temperatureInFahrenheit;
}
