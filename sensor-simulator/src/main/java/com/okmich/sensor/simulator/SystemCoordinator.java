/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.sensor.simulator;

import com.okmich.sensor.simulator.model.Reading;
import static com.okmich.sensor.simulator.OptionRegistry.*;
import com.okmich.sensor.simulator.gui.ApplicationFrame;
import com.okmich.sensor.simulator.net.NetworkInterface;
import com.okmich.sensor.simulator.net.CommandReceiverNetworkInterface;
import com.okmich.sensor.simulator.net.DataFlowHandler;
import com.okmich.sensor.simulator.net.DataFlowNetworkInterface;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author m.enudi
 */
public class SystemCoordinator {

    private final NetworkInterface networkInterface;
    private final ApplicationFrame appFrame;
    private final ScheduledExecutorService executorService;
    /**
     * LOG
     */
    private static final Logger LOG = Logger.getLogger(SystemCoordinator.class.getName());

    /**
     *
     * @param networkInterface
     * @param applicationFrame
     */
    public SystemCoordinator(NetworkInterface networkInterface, ApplicationFrame applicationFrame) {
        this.networkInterface = networkInterface;
        this.appFrame = applicationFrame;
        this.executorService = Executors.newSingleThreadScheduledExecutor();
    }

    /**
     *
     */
    public void start() {
        //initiate network connection
        try {
            this.networkInterface.initConnectionToServer(value(DEVICE_ID));
            this.appFrame.setConnectionStatus(Status.STATUS_ON);
        } catch (IOException | IllegalStateException ex) {
            LOG.log(Level.SEVERE, null, ex);
            System.exit(-1);
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, null, ex);
        }
        try {
            //begin loop
            this.executorService.scheduleWithFixedDelay(new ScheduledWorker(), 10, valueAsInteger(TXNFR_FREQ), TimeUnit.SECONDS);
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, null, ex);
        }
        //start up the command reception if this is a valve station
        if (value(TYPE).equals(Status.STN_TYPE_VALVE)) {
            try {
                //start another thread that listens for command from control
                new Thread(new CommandReceiverNetworkInterface()).start();
            } catch (IOException ex) {
                LOG.log(Level.SEVERE, null, ex);
                System.exit(-1);
            }
        }
    }

    /**
     *
     */
    private class ScheduledWorker implements Runnable {

        private final DataFlowNetworkInterface dataFlowNetworkInterface;

        public ScheduledWorker() throws IOException {
            dataFlowNetworkInterface = new DataFlowNetworkInterface(new DataFlowHanderImpl());
        }

        @Override
        public void run() {
            if (appFrame.isDisconnectedMode()) {
                //the mode is disconnected
                appFrame.setConnectionStatus(Status.STATUS_OFF);
                appFrame.setFlowStatus(Status.STATUS_OFF);
                return;
            }
            try {
                //get new data for reading
                dataFlowNetworkInterface.requestData(OptionRegistry.value(OptionRegistry.DEVICE_ID));
            } catch (Exception ex) {
                Logger.getLogger(ScheduledWorker.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
                appFrame.setConnectionStatus(Status.STATUS_OFF);
                appFrame.setFlowStatus(Status.STATUS_OFF);
            }
        }
    }

    /**
     *
     */
    private class DataFlowHanderImpl implements DataFlowHandler {

        @Override
        public void handleDataFlowResponse(String response) {
            try {
                //send data to server
                Reading reading = DataGenerator.generate(response, appFrame.getMode());
                networkInterface.transferDataToServer(reading);
                appFrame.setConnectionStatus(Status.STATUS_ON);
                appFrame.setFlowStatus(reading.isFlowActive() ? Status.STATUS_ON : Status.STATUS_STALE);
                //send to application frame for display
                appFrame.refreshTableData(reading);
            } catch (Exception ex) {
                Logger.getLogger(SystemCoordinator.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
