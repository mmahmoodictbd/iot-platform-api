package com.unloadbrain.assignement.qardio.service;

import com.unloadbrain.assignement.qardio.dto.response.DataCollectionStatusResponse;

import java.util.List;

/**
 * Generic collector service APIs.
 */
public interface SensorDataCollectorService<Data> {

    DataCollectionStatusResponse collect(Data data);

    List<DataCollectionStatusResponse> collect(List<Data> dataList);
}
