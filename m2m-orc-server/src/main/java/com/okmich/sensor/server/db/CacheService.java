/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.sensor.server.db;

import com.okmich.sensor.server.model.Sensor;
import com.okmich.sensor.server.model.SensorReading;
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
     * @param sensors
     */
    void saveSensors(List<Sensor> sensors);

    /**
     *
     * @param sensorReading
     */
    void saveSensorReading(SensorReading sensorReading);

}
