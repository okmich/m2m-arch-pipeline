/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.sensor.simulator.service.handler;

import com.okmich.sensor.simulator.FlowStates;
import com.okmich.sensor.simulator.SystemCoordinator;
import com.okmich.sensor.simulator.TransitionCommands;
import com.okmich.sensor.simulator.gui.UserInterface;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author datadev
 */
public class CommandReceiverHandler implements DataHandler {

    private final UserInterface ui;

    private final static Logger LOG = Logger.getLogger(SystemCoordinator.class.getName());

    public CommandReceiverHandler(UserInterface userInterface) {
        this.ui = userInterface;
    }

    @Override
    public void handle(String response) {
        try {
            //interpret command from the server and effect on the entire system
            //list of commands include
            //DEFLT,PTOFF,PUTON,INCRX,DECRX
            LOG.log(Level.INFO, "executing command {0}", response);
            String[] parts = response.split(";");
            TransitionCommands trnsCmd = TransitionCommands.valueOf(parts[0]);
            LOG.log(Level.INFO, trnsCmd.toString());
            switch (trnsCmd) {
                case PTOFF:
                    ui.setMode(FlowStates.STOPFLOW.toString());
                    break;
                case PUTON:
                case DEFLT:
                    ui.setMode(FlowStates.NORMAL.toString());
                default:
            }
            LOG.log(Level.INFO, "executed command {0}", response);
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, null, ex);
        }
    }
}
