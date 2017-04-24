/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.sensor.server.db.impl;

import java.util.HashMap;
import java.util.Map;
import com.okmich.sensor.server.db.SensorReadingDAO;
import com.okmich.sensor.server.model.SensorReading;

/**
 *
 * @author m.enudi
 */
public class InMemorySensorReadingDAO implements SensorReadingDAO {

    private final Map<String, SensorReading> nodeReadingMap;

    public InMemorySensorReadingDAO() {
        this.nodeReadingMap = new HashMap<>();
    }

    @Override
    public void saveSensorReading(SensorReading sensorReading) {
        this.nodeReadingMap.put("", sensorReading);
    }

    @Override
    public SensorReading cacheSensorLatestReading(String devId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public SensorReading getSensorLatestReading(String devId) {
        return this.nodeReadingMap.get(devId);
    }

}
