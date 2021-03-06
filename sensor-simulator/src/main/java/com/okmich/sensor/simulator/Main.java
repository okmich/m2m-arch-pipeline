/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.sensor.simulator;

import static com.okmich.sensor.simulator.OptionRegistry.*;
import com.okmich.sensor.simulator.gui.ApplicationFrame;
import com.okmich.sensor.simulator.service.DataTransferServerInterface;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author m.enudi
 */
public class Main {

    private final DataTransferServerInterface networkInterface;
    public final ApplicationFrame applicationFrame;
    public final SystemCoordinator systemCoordinator;

    /**
     *
     * @param devID
     * @throws IOException
     */
    public Main(String devID) throws IOException {
        this.networkInterface = new DataTransferServerInterface();
        this.applicationFrame = new ApplicationFrame(value(TYPE));
        this.systemCoordinator = new SystemCoordinator(networkInterface, applicationFrame);
    }

    public static void main(String[] args) {
        try {
            OptionRegistry.initialize(args);

            //initialize the DataGenerator
            DataFlowSimulationGenerator.initialize();
            new Main(value(DEVICE_ID)).startApp();
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void startApp() {
        this.systemCoordinator.start();
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            applicationFrame.setVisible(true);
        });
    }
}
