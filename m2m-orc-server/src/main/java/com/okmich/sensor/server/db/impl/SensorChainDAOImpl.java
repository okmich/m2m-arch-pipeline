/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.sensor.server.db.impl;

import static com.okmich.sensor.server.OptionRegistry.REDIS_SERVER_ADDRESS;
import static com.okmich.sensor.server.OptionRegistry.REDIS_SERVER_PORT;
import static com.okmich.sensor.server.OptionRegistry.value;
import static com.okmich.sensor.server.OptionRegistry.valueAsInteger;
import com.okmich.sensor.server.db.SensorChainDAO;
import com.okmich.sensor.server.model.Sensor;
import java.util.List;
import redis.clients.jedis.Jedis;

/**
 *
 * @author m.enudi
 */
public class SensorChainDAOImpl implements SensorChainDAO {

    private final Jedis jedis;

    private static final String CHAIN_HASH_KEY = "sensor.chain";
    /**
     *
     */
    private static final String FROM_PREFIX = "0";
    /**
     *
     */
    private static final String TO_PREFIX = "1";

    /**
     *
     */
    public SensorChainDAOImpl() {
        jedis = new Jedis(value(REDIS_SERVER_ADDRESS),
                valueAsInteger(REDIS_SERVER_PORT), 30000);
    }

    @Override
    public void loadSensorChain(List<Sensor> sensors) {
        String sDevId, devId;
        for (Sensor sensor : sensors) {
            sDevId = sensor.getSupplyDevId();
            devId = sensor.getDevId();
            if (sDevId != null && !sDevId.trim().isEmpty()) {
                jedis.hset(CHAIN_HASH_KEY, FROM_PREFIX + devId, sDevId);
                jedis.hset(CHAIN_HASH_KEY, TO_PREFIX + sDevId, devId);
            }
        }
    }

    @Override
    public String getOrgFromDevID(String devId) {
        return jedis.hget(CHAIN_HASH_KEY, FROM_PREFIX + devId);
    }

    @Override
    public String getOrgToDevID(String devId) {
        return jedis.hget(CHAIN_HASH_KEY, TO_PREFIX + devId);
    }

    @Override
    public void saveSensorChain(String devId, String fromID, String toID) {
        if (devId != null && fromID != null) {
            jedis.hset(CHAIN_HASH_KEY, FROM_PREFIX + devId, fromID);
        }
        if (devId != null && toID != null) {
            jedis.hset(CHAIN_HASH_KEY, TO_PREFIX + devId, toID);
        }
    }

    @Override
    public String getFromDevID(String devId) {
        return jedis.hget(CHAIN_HASH_KEY, FROM_PREFIX + devId);
    }

    @Override
    public String getToDevID(String devId) {
        return jedis.hget(CHAIN_HASH_KEY, TO_PREFIX + devId);
    }
}
