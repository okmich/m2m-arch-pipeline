/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.sensor.server.db;

import com.okmich.sensor.server.model.Sensor;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

/**
 *
 * @author m.enudi
 */
public class InMemorySensorDAO implements SensorDAO {

    private final Map<String, Sensor> nodeMap;

    public InMemorySensorDAO() {
        this.nodeMap = new HashMap<>();
    }

    @Override
    public void saveSensor(Sensor node) {
        this.nodeMap.put(node.getDevId(), node);
    }

    @Override
    public Sensor getSensor(String devId) {
        return this.nodeMap.get(devId);
    }

    @Override
    public List<Sensor> getAllSensors() {
        return null;
    }

}
