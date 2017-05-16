/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.sensor.server.db;

import redis.clients.jedis.Jedis;

/**
 *
 * @author m.enudi
 */
public interface CacheService extends SensorCacheService, SensorReadingCacheService {

    default Jedis getJedis() {

        return null;
    }
}
