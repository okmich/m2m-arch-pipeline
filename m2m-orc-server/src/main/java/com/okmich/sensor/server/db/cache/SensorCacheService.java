/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.sensor.server.db.cache;

import com.okmich.sensor.server.model.Sensor;
import java.util.List;
import redis.clients.jedis.JedisPool;

/**
 *
 * @author datadev
 */
public interface SensorCacheService {

    String SENSOR_HASH_KEY = "sensors";

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
     * @param jedisPool 
     */
    void setPool(JedisPool jedisPool);

}
