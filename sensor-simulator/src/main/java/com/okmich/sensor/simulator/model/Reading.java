/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.sensor.simulator.model;

import java.io.Serializable;

/**
 *
 * @author m.enudi
 */
public class Reading implements Serializable {

    private String devId;
    private Long timestamp;
    private Float pressure;
    private Float temperature;
    private Float volume;
    private Float flowVelocity;
    private Float extBodyForce;

    public Reading() {
        this.timestamp = System.currentTimeMillis();
    }

    public Reading(String devId) {
        this();
        this.devId = devId;
    }

    /**
     *
     * @param str
     * @return
     */
    public static Reading fromString(String str) {
        String[] parts = str.split(";");
        Reading r = new Reading();
        r.devId = (parts[0]);
        r.timestamp = (Long.valueOf(parts[1]));
        r.pressure = (getFloat(parts[2]));
        r.temperature = (getFloat(parts[3]));
        r.volume = (getFloat(parts[4]));
        r.flowVelocity = (getFloat(parts[5]));
        r.extBodyForce = (getFloat(parts[6]));

        return r;
    }

    /**
     *
     * @param str
     * @param devId
     * @return
     */
    public static Reading newFromString(String str, String devId) {
        String[] parts = str.split(";");
        Reading r = new Reading();
        r.devId = (devId);
        r.pressure = (getFloat(parts[2]));
        r.temperature = (getFloat(parts[3]));
        r.volume = (getFloat(parts[4]));
        r.flowVelocity = (getFloat(parts[5]));
        r.extBodyForce = (getFloat(parts[6]));

        return r;
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

    public boolean isFlowActive() {
        return this.volume > 0.0f || this.flowVelocity > 0.0f;
    }

    @Override
    public String toString() {
        return devId + ";" + timestamp + ";" + asString(pressure)
                + ";" + asString(temperature) + ";" + asString(volume) + ";"
                + asString(flowVelocity) + ";" + asString(extBodyForce);
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
}
