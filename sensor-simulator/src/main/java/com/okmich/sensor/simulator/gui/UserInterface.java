/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.sensor.simulator.gui;

import com.okmich.sensor.simulator.model.Reading;

/**
 *
 * @author datadev
 */
public interface UserInterface {

    String getMode();

    boolean isDisconnectedMode();

    /**
     *
     * @param data
     */
    void refreshTableData(Reading data);

    void setConnectionStatus(int connStatus);

    void setFlowStatus(int connStatus);

    void setMode(String mode);
    
}
