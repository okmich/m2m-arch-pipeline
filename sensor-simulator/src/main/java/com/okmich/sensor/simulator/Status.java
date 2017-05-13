/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.sensor.simulator;

/**
 *
 * @author m.enudi
 */
public interface Status {

    int STATUS_OFF = 0;
    int STATUS_ON = 1;
    int STATUS_NO_FLOW = 2;
    
    /**
     * 
     */
    String STN_TYPE_BASE_STATION = "bs";
    String STN_TYPE_NOT_BASE_STATION = "ns";
}
