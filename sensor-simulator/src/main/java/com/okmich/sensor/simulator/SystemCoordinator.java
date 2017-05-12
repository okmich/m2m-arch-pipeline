/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.sensor.simulator;

import static com.okmich.sensor.simulator.OptionRegistry.*;
import com.okmich.sensor.simulator.gui.UserInterface;
import com.okmich.sensor.simulator.service.CommandReceiverServerInterface;
import com.okmich.sensor.simulator.service.DataFlowServerInterface;
import com.okmich.sensor.simulator.service.DataTransferServerInterface;
import com.okmich.sensor.simulator.service.handler.CommandReceiverHandler;
import com.okmich.sensor.simulator.service.handler.DataFlowHanderImpl;
import com.okmich.sensor.simulator.service.handler.DataHandler;
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

    private final DataTransferServerInterface networkInterface;
    private final UserInterface userInterface;
    private final ScheduledExecutorService executorService;
    /**
     * LOG
     */
    private static final Logger LOG = Logger.getLogger(SystemCoordinator.class.getName());

    /**
     *
     * @param dtServerInterface
     * @param userInterface
     */
    public SystemCoordinator(DataTransferServerInterface dtServerInterface, UserInterface userInterface) {
        this.networkInterface = dtServerInterface;
        this.userInterface = userInterface;
        this.executorService = Executors.newSingleThreadScheduledExecutor();
    }

    /**
     *
     */
    public void start() {
        //initiate network connection
        try {
            this.networkInterface.initConnectionToServer(value(DEVICE_ID));
            this.userInterface.setConnectionStatus(Status.STATUS_ON);
        } catch (IOException | IllegalStateException ex) {
            LOG.log(Level.SEVERE, null, ex);
            System.exit(-1);
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, null, ex);
        }
        try {
            //begin loop
            this.executorService.scheduleWithFixedDelay(new ScheduledWorker(),
                    10,
                    valueAsInteger(TXNFR_FREQ),
                    TimeUnit.SECONDS);
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, null, ex);
        }
        //start up the command reception if this is a valve station
        if (value(TYPE).equals(Status.STN_TYPE_VALVE)) {
            DataHandler controlCommandHandler = new CommandReceiverHandler(this.userInterface);
            try {
                //start another thread that listens for command from control
                new Thread(new CommandReceiverServerInterface(controlCommandHandler)).start();
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

        private final DataFlowServerInterface dataFlowNetworkInterface;
        private final DataHandler dataFlowHandler;

        public ScheduledWorker() throws IOException {
            this.dataFlowHandler = new DataFlowHanderImpl(networkInterface, userInterface);
            dataFlowNetworkInterface = new DataFlowServerInterface(dataFlowHandler);
        }

        @Override
        public void run() {
            if (userInterface.isDisconnectedMode()) {
                //the mode is disconnected
                userInterface.setConnectionStatus(Status.STATUS_OFF);
                userInterface.setFlowStatus(Status.STATUS_OFF);
                return;
            }
            if (userInterface.getFlowStatus() == Status.STATUS_NO_FLOW) {
                dataFlowHandler.handle(DataFlowSimulationGenerator.generateNoFlowReading().toString());
            }
            try {
                //get new data for reading
                dataFlowNetworkInterface.requestData(value(DEVICE_ID));
            } catch (Exception ex) {
                Logger.getLogger(ScheduledWorker.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
                userInterface.setConnectionStatus(Status.STATUS_OFF);
                userInterface.setFlowStatus(Status.STATUS_OFF);
            }
        }
    }
}
