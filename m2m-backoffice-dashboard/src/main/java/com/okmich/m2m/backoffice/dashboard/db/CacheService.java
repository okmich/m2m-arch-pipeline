/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.m2m.backoffice.dashboard.db;

import com.okmich.m2m.backoffice.dashboard.model.Sensor;
import java.util.List;

/**
 *
 * @author m.enudi
 */
public interface CacheService {

    String SENSOR_HASH_KEY = "sensors";
    String M2M_PRODUCTION = "m2m.prod";

    /**
     *
     * @param devId
     * @return
     */
    Sensor getSensor(String devId);

    /**
     *
     * @return
     */
    List<Sensor> getSensors();

    /**
     * 
     * @param ts
     * @return 
     */
    Float getDailyProduction(long ts);

}
