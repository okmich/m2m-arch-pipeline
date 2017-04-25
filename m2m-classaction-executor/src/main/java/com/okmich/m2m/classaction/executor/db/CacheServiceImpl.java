/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.m2m.classaction.executor.db;

import static com.okmich.m2m.classaction.executor.OptionRegistry.*;
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

    @Override
    public String getSensorSupplyBaseDeviceId(String devId) {
        String sensorStr = jedis.get(devId);
        if (sensorStr == null || sensorStr.isEmpty()) {
            return devId;
        }
        //if not null
        String[] parts = sensorStr.split(";");
        //get the bsdevId
        return parts[5] == null || parts[5].isEmpty() ? devId : parts[5];
    }
}
