/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.sensor.server.model;

import java.io.Serializable;

/**
 *
 * @author m.enudi
 */
public class SensorReading implements Serializable {

    //devId;ts;prs;tmp;vol;flv;xbf
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
        //devId;ts;prs;tmp;vol;flv;xbf
        String[] fields = readings.split(";");
        this.devId = (fields[0]);
        this.timestamp = (Long.valueOf(fields[1]));
        this.pressure = (getFloat(fields[2]));
        this.temperature = (getFloat(fields[3]));
        this.volume = (getFloat(fields[4]));
        this.flowVelocity = (getFloat(fields[5]));
        this.extBodyForce = (getFloat(fields[6]));
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

    private static String asString(Number val) {
        if (val == null) {
            return "";
        } else {
            return val.toString();
        }
    }

    private static Float getFloat(String s) {
        if (s == null || s.isEmpty()) {
            return null;
        } else {
            return Float.valueOf(s);
        }
    }

    @Override
    public String toString() {
        return devId + ";" + timestamp + ";" + asString(pressure)
                + ";" + asString(temperature) + ";" + asString(volume) + ";"
                + asString(flowVelocity) + ";" + asString(extBodyForce);
    }
}
