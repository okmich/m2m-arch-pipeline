/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.sensor.server.db.impl;

import com.okmich.sensor.server.db.CacheService;
import static com.okmich.sensor.server.OptionRegistry.*;
import com.okmich.sensor.server.model.Sensor;
import com.okmich.sensor.server.model.SensorReading;
import redis.clients.jedis.Jedis;

/**
 *
 * @author m.enudi
 */
public final class CacheServiceImpl implements CacheService {

    private final Jedis jedis;

    public CacheServiceImpl() {
        jedis = new Jedis(value(REDIS_SERVER_ADDRESS),
                valueAsInteger(REDIS_SERVER_PORT), 30000);
    }

    /**
     *
     * @param sensor
     */
    @Override
    public void saveSensor(Sensor sensor) {
        jedis.set(sensor.getDevId(), sensor.toString());
    }

    /**
     *
     * @param devId
     * @return
     */
    @Override
    public Sensor getSensor(String devId) {
        String record = jedis.get(devId);
        if (record == null || record.trim().isEmpty()) {
            return null;
        }
        return new Sensor(record);
    }

    /**
     *
     * @param sensorReading
     */
    @Override
    public void saveSensorReading(SensorReading sensorReading) {
        jedis.set(sensorReading.getDevId(), sensorReading.toString());
    }

    /**
     *
     * @param devId
     * @return
     */
    @Override
    public SensorReading getSensorReading(String devId) {
        String record = jedis.get(devId);
        if (record == null || record.trim().isEmpty()) {
            return null;
        }
        return new SensorReading(record);
    }
}
