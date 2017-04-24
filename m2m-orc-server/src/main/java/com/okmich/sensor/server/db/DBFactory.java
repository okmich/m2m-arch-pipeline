/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.sensor.server.db;

import com.okmich.sensor.server.db.impl.InMemorySensorDAO;
import com.okmich.sensor.server.db.impl.InMemorySensorReadingDAO;

/**
 *
 * @author m.enudi
 */
public final class DBFactory {

    private static SensorDAO sensorDAO;
    private static SensorChainDAO sensorChainDAO;
    private static SensorReadingDAO sensorDataDao;

    /**
     *
     */
    public static void initialize() {
        sensorDAO = new InMemorySensorDAO();
        sensorDataDao = new InMemorySensorReadingDAO();
    }

    /**
     *
     * @return
     */
    public static SensorDAO getSensorDao() {
        return sensorDAO;
    }

    /**
     *
     * @return
     */
    public static SensorChainDAO getSensorChainDAO() {
        return sensorChainDAO;
    }
    /**
     *
     * @return
     */
    public static SensorReadingDAO getSensorDataDao() {
        return sensorDataDao;
    }
}
