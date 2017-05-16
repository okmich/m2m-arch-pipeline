/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.m2m.backoffice.dashboard.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author datadev
 */
public class SensorEvent {

    public static final String NORMAL = "NOR";
    public static final String TURBULENCE = "TUB";
    public static final String LEAKAGE = "LKG";

    private final static DateFormat DF = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");

    private String devId;
    private long ts;
    private String clazz;
    private String arg;
    private String status;

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
     * @return the ts
     */
    public long getTs() {
        return ts;
    }

    /**
     * @param ts the ts to set
     */
    public void setTs(long ts) {
        this.ts = ts;
    }

    /**
     * @return the clazz
     */
    public String getClazz() {
        return clazz;
    }

    /**
     * @param clazz the clazz to set
     */
    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    /**
     * @return the arg
     */
    public String getArg() {
        return arg;
    }

    /**
     * @param arg the arg to set
     */
    public void setArg(String arg) {
        this.arg = arg;
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return the time in string format
     */
    public String getDateTimeAsString() {
        return DF.format(new Date(this.ts));
    }
}
