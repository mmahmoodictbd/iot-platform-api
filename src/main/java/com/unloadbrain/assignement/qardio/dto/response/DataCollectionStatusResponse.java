package com.unloadbrain.assignement.qardio.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO class to return status if data collection process is successful or not as HTTP response.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DataCollectionStatusResponse {

    private boolean success;
}
