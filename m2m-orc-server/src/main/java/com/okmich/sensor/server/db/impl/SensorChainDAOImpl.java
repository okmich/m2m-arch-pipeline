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
import java.util.Map;
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
    private static final String PRE = "0";
    /**
     *
     */
    private static final String POST = "1";

    /**
     *
     */
    public SensorChainDAOImpl() {
        jedis = new Jedis(value(REDIS_SERVER_ADDRESS),
                valueAsInteger(REDIS_SERVER_PORT), 30000);
    }

    @Override
    public void loadSensorChain(Map<String, String> chainMap) {
        String[] vals;
        for (String key : chainMap.keySet()) {
            vals = chainMap.get(key).split("-");
            jedis.hset(CHAIN_HASH_KEY, PRE + key, vals[0]);
            jedis.hset(CHAIN_HASH_KEY, POST + key, vals[1]);
        }
    }

    @Override
    public String getOrgFromDevID(String devId) {
        return jedis.hget(CHAIN_HASH_KEY, PRE + devId);
    }

    @Override
    public String getOrgToDevID(String devId) {
        return jedis.hget(CHAIN_HASH_KEY, POST + devId);
    }

    @Override
    public void saveSensorChain(String devId, String fromID, String toID) {
        jedis.hset(CHAIN_HASH_KEY, PRE + devId, fromID);
        jedis.hset(CHAIN_HASH_KEY, POST + devId, toID);
    }

    @Override
    public String getFromDevID(String devId) {
        return jedis.hget(CHAIN_HASH_KEY, PRE + devId);
    }

    @Override
    public String getToDevID(String devId) {
        return jedis.hget(CHAIN_HASH_KEY, POST + devId);
    }
}
