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

    String M2M_SENSORS = "m2m.sensors";

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

}
