/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.sensor.server.db.cache.impl;

import static com.okmich.sensor.server.OptionRegistry.REDIS_SERVER_ADDRESS;
import static com.okmich.sensor.server.OptionRegistry.REDIS_SERVER_PORT;
import static com.okmich.sensor.server.OptionRegistry.value;
import static com.okmich.sensor.server.OptionRegistry.valueAsInteger;
import com.okmich.sensor.server.db.cache.SensorReadingCacheService;
import com.okmich.sensor.server.db.cache.SensorCacheService;
import com.okmich.sensor.server.db.cache.CacheService;
import com.okmich.sensor.server.model.Sensor;
import com.okmich.sensor.server.model.SensorReading;
import java.util.List;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

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
        JedisPool pool = new JedisPool(new JedisPoolConfig(),
                value(REDIS_SERVER_ADDRESS),
                valueAsInteger(REDIS_SERVER_PORT), 3000);
        
        this.sensorCacheService = scs;
        this.sensorCacheService.setPool(pool);
        
        this.sensorReadingCacheService = srcs;
        this.sensorReadingCacheService.setPool(pool);

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

    @Override
    public void setPool(JedisPool jedisPool) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
