/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.sensor.simulator.service.handler;

import com.okmich.sensor.simulator.DataGenerator;
import com.okmich.sensor.simulator.Status;
import com.okmich.sensor.simulator.SystemCoordinator;
import com.okmich.sensor.simulator.gui.UserInterface;
import com.okmich.sensor.simulator.model.Reading;
import com.okmich.sensor.simulator.service.DataTransferServerInterface;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author datadev
 */
public class DataFlowHanderImpl implements DataHandler {

    private final DataTransferServerInterface dtServiceInterface;
    private final UserInterface userInterface;

    /**
     *
     * @param dtServiceInterface
     * @param userInterface
     */
    public DataFlowHanderImpl(
            DataTransferServerInterface dtServiceInterface,
            UserInterface userInterface) {
        this.dtServiceInterface = dtServiceInterface;
        this.userInterface = userInterface;
    }

    @Override
    public void handle(String response) {
        try {
            //send data to server
            Reading reading = DataGenerator.generate(response, userInterface.getMode());
            dtServiceInterface.transferDataToServer(reading);
            userInterface.setConnectionStatus(Status.STATUS_ON);
            userInterface.setFlowStatus(reading.isFlowActive() ? Status.STATUS_ON : Status.STATUS_STALE);
            //send to application frame for display
            userInterface.refreshTableData(reading);
        } catch (Exception ex) {
            Logger.getLogger(SystemCoordinator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
