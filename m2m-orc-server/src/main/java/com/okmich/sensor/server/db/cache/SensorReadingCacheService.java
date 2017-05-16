/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.sensor.server.db.cache;

import com.okmich.sensor.server.model.SensorReading;
import redis.clients.jedis.JedisPool;

/**
 *
 * @author m.enudi
 */
public interface SensorReadingCacheService {

    String M2M_PRODUCTION = "m2m.prod";
    String READING_HASH_KEY = "sensor.reading";

    /**
     *
     * @param devId
     * @return
     */
    SensorReading getSensorReading(String devId);

    /**
     *
     * @param sensorReading
     */
    void saveSensorReading(SensorReading sensorReading);

    /**
     *
     * @param vol
     * @param ts
     */
    void updateDailyProduction(double vol, long ts);

    /**
     *
     * @param jedisPool
     */
    void setPool(JedisPool jedisPool);

}
