/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.sensor.server.model;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author m.enudi
 */
public class Sensor implements Serializable {

    private String devId;
    private String type;
    private String address;
    private String supplyDevId;
    private String baseStationDevId;
    private float distSupplyStation;
    private String geo;

    public Sensor() {
    }

    /**
     *
     * @param devId
     */
    public Sensor(String devId) {
        this.devId = devId;
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
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return the supplyDevId
     */
    public String getSupplyDevId() {
        return supplyDevId;
    }

    /**
     * @param supplyDevId the supplyDevId to set
     */
    public void setSupplyDevId(String supplyDevId) {
        this.supplyDevId = supplyDevId;
    }

    /**
     * @return the baseStationDevId
     */
    public String getBaseStationDevId() {
        return baseStationDevId;
    }

    /**
     * @param baseStationDevId the baseStationDevId to set
     */
    public void setBaseStationDevId(String baseStationDevId) {
        this.baseStationDevId = baseStationDevId;
    }

    /**
     * @return the distSupplyStation
     */
    public float getDistSupplyDev() {
        return distSupplyStation;
    }

    /**
     * @param distSupplyDev the distSupplyStation to set
     */
    public void setDistSupplyDev(float distSupplyDev) {
        this.distSupplyStation = distSupplyDev;
    }

    /**
     * @return the geo
     */
    public String getGeo() {
        return geo;
    }

    /**
     * @param geo the geo to set
     */
    public void setGeo(String geo) {
        this.geo = geo;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 73 * hash + Objects.hashCode(this.devId);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Sensor other = (Sensor) obj;
        if (!Objects.equals(this.devId, other.devId)) {
            return false;
        }
        return true;
    }

    public static Sensor valueOf(String payload) {
        //devId;type;add;sDevId;dsbs;bsdev;lct;geo
        String[] parts = payload.split(";");

        Sensor s = new Sensor();

        s.devId = parts[0];
        s.type = parts[1];
        s.address = parts[2];
        s.supplyDevId = parts[3];
        s.baseStationDevId = parts[4];
        s.distSupplyStation = parts[5] == null || parts[5].isEmpty() ? 0f : Float.parseFloat(parts[5]);
        s.geo = parts[6];

        return s;
    }

    @Override
    public String toString() {
        return String.format("%s;%s;%s;%s;%s;%s;%s",
                devId,
                type,
                address,
                supplyDevId,
                baseStationDevId,
                distSupplyStation,
                geo);
    }
}
