/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.sensor.server.model;

import static com.okmich.sensor.server.OptionRegistry.*;
import com.okmich.sensor.server.util.Util;
import java.io.Serializable;
import java.util.Map;

/**
 *
 * @author m.enudi
 */
public class Sensor implements Serializable {

    private String devId;
    private String type;
    private String address;
    private String baseStationDevId;
    private float distSupplyStation;
    private long lastConnectTime;
    private String geo;

    public Sensor() {
    }

    /**
     *
     * @param payload
     */
    public Sensor(String payload) {
        //devId:zzzz;type:nssen|bssen;add:ipaddress;dsbs:300;bsdev:jjjjj;geo:23.030,-203
        Map<String, String> map = Util.parseStringDataAsMap(payload);
        this.devId = map.get(DEVICE_ID);
        this.type = map.get(TYPE);
        this.address = map.get(ADDRESS);
        this.baseStationDevId = map.get(BS_DEV_ID);
        this.distSupplyStation = Float.parseFloat(map.get(DIST_SUPPLY_STN));
        this.geo = map.get(GEO);
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
     * @return the lastConnectTime
     */
    public long getLastConnectTime() {
        return lastConnectTime;
    }

    /**
     * @param lastConnectTime the lastConnectTime to set
     */
    public void setLastConnectTime(long lastConnectTime) {
        this.lastConnectTime = lastConnectTime;
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
    public String toString() {
        return "devId:" + devId + ";type:" + type
                + ";add:" + address + ";dsbs:" + distSupplyStation
                + ";bsdev:" + baseStationDevId + ";lct:" + lastConnectTime
                + ";geo:" + geo;
    }
}
