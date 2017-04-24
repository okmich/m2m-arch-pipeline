/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.sensor.server.net.handler;

import static com.okmich.sensor.server.OptionRegistry.*;
import com.okmich.sensor.server.db.DBFactory;
import com.okmich.sensor.server.db.SensorDAO;
import com.okmich.sensor.server.model.Sensor;
import com.okmich.sensor.server.util.Util;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author m.enudi
 */
public class ConnectionInitiationHandler extends Handler {

    private static final Logger LOG = Logger.getLogger(ConnectionInitiationHandler.class.getName());

    /**
     *
     */
    public ConnectionInitiationHandler() {
    }

    @Override
    public String handle(String request) {
        Map<String, String> fields = Util.parseStringDataAsMap(request);
        LOG.log(Level.INFO, request);

        SensorDAO sensorDAO = DBFactory.getSensorDao();
        Sensor sensor = sensorDAO.getSensor(fields.get(DEVICE_ID));
        if (sensor == null) {
            throw new IllegalArgumentException("device node already exist");
        }
        sensor.setAddress(fields.get(ADDRESS));
        sensor.setType(fields.get(TYPE));
        sensor.setBaseStationDevId(fields.get(BS_DEV_ID));
        sensor.setDistSupplyDev(Float.parseFloat(fields.get(DIST_SUPPLY_STN)));
        sensor.setGeo(fields.get(GEO));
        sensor.setLastConnectTime(System.currentTimeMillis());
        //update to db
        sensorDAO.saveSensor(sensor);

        return "initiated";
    }
}
