/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.sensor.server.db;

import com.okmich.sensor.server.model.SensorReading;

/**
 *
 * @author m.enudi
 */
public interface SensorReadingDAO {

    void saveSensorReading(SensorReading sensorReading);

    SensorReading cacheSensorLatestReading(String devId);

    SensorReading getSensorLatestReading(String devId);
}
