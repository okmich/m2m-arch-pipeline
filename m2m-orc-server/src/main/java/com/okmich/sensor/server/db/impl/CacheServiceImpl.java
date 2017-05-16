/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.sensor.server.db.impl;

import com.okmich.sensor.server.db.*;
import com.okmich.sensor.server.model.Sensor;
import com.okmich.sensor.server.model.SensorReading;
import java.util.List;

/**
 *
 * @author m.enudi
 */
public final class CacheServiceImpl implements CacheService {

    private final SensorCacheService sensorCacheService;
    private final SensorReadingCacheService sensorReadingCacheService;

    /**
     *
     * @param scs
     * @param srcs
     */
    public CacheServiceImpl(SensorCacheService scs, SensorReadingCacheService srcs) {
        this.sensorCacheService = scs;
        this.sensorReadingCacheService = srcs;
    }

    @Override
    public Sensor getSensor(String devId) {
        return this.sensorCacheService.getSensor(devId);
    }

    @Override
    public List<Sensor> getSensors() {
        return sensorCacheService.getSensors();
    }

    @Override
    public void saveSensor(Sensor sensor) {
        sensorCacheService.saveSensor(sensor);
    }

    @Override
    public void saveSensors(List<Sensor> sensors) {
        this.sensorCacheService.saveSensors(sensors);
    }

    @Override
    public SensorReading getSensorReading(String devId) {
        return this.sensorReadingCacheService.getSensorReading(devId);
    }

    @Override
    public void saveSensorReading(SensorReading sensorReading) {
        sensorReadingCacheService.saveSensorReading(sensorReading);
    }

    @Override
    public void updateDailyProduction(double vol, long ts) {
        this.sensorReadingCacheService.updateDailyProduction(vol, ts);
    }

}
