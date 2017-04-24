/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.sensor.server.db;

import com.okmich.sensor.server.model.Sensor;
import com.okmich.sensor.server.model.SensorReading;

/**
 *
 * @author m.enudi
 */
public interface CacheService {

    /**
     *
     * @param devId
     * @return
     */
    Sensor getSensor(String devId);

    /**
     *
     * @param devId
     * @return
     */
    SensorReading getSensorReading(String devId);

    /**
     *
     * @param sensor
     */
    void saveSensor(Sensor sensor);

    /**
     *
     * @param sensorReading
     */
    void saveSensorReading(SensorReading sensorReading);

}
