/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.sensor.server.model;

import com.okmich.sensor.server.util.Util;
import java.io.Serializable;
import java.util.Map;

/**
 *
 * @author m.enudi
 */
public class SensorReading implements Serializable {

    //"devId:" + devId + ";ts:" + timestamp + ";prs:" + pressure + ";tmp:" + temperature + ";vol:" + volume + ";flv:" + flowVelocity + ";xbf:" + extBodyForce;
    private String devId;
    private long timestamp;
    private float pressure;
    private float temperature;
    private float volume;
    private float flowVelocity;
    private float extBodyForce;

    public SensorReading() {
    }

    public SensorReading(String readings) {
        Map<String, String> map = Util.parseStringDataAsMap(readings);
        this.devId = map.get("devId");
        this.timestamp = Long.parseLong(map.get("ts"));
        this.pressure = Float.parseFloat(map.get("prs"));
        this.temperature = Float.parseFloat(map.get("tmp"));
        this.volume = Float.parseFloat(map.get("vol"));
        this.flowVelocity = Float.parseFloat(map.get("flv"));
        this.extBodyForce = Float.parseFloat(map.get("xbf"));
    }

    /**
     * @return the devId
     */
    public String getDevId() {
        return devId;
    }

    /**
     * @param devId the devId to set
     */
    public void setDevId(String devId) {
        this.devId = devId;
    }

    /**
     * @return the timestamp
     */
    public long getTimestamp() {
        return timestamp;
    }

    /**
     * @param timestamp the timestamp to set
     */
    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * @return the pressure
     */
    public float getPressure() {
        return pressure;
    }

    /**
     * @param pressure the pressure to set
     */
    public void setPressure(float pressure) {
        this.pressure = pressure;
    }

    /**
     * @return the temperature
     */
    public float getTemperature() {
        return temperature;
    }

    /**
     * @param temperature the temperature to set
     */
    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    /**
     * @return the volume
     */
    public float getVolume() {
        return volume;
    }

    /**
     * @param volume the volume to set
     */
    public void setVolume(float volume) {
        this.volume = volume;
    }

    /**
     * @return the flowVelocity
     */
    public float getFlowVelocity() {
        return flowVelocity;
    }

    /**
     * @param flowVelocity the flowVelocity to set
     */
    public void setFlowVelocity(float flowVelocity) {
        this.flowVelocity = flowVelocity;
    }

    /**
     * @return the extBodyForce
     */
    public float getExtBodyForce() {
        return extBodyForce;
    }

    /**
     * @param extBodyForce the extBodyForce to set
     */
    public void setExtBodyForce(float extBodyForce) {
        this.extBodyForce = extBodyForce;
    }

    @Override
    public String toString() {
        return "devId:" + this.devId + ";ts:" + timestamp
                + ";prs:" + pressure + ";tmp:" + temperature + ";vol:" + volume
                + ";flv:" + flowVelocity + ";xbf:" + extBodyForce;
    }
}
