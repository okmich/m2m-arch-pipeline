/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.m2m.classaction.executor;

import com.okmich.m2m.classaction.executor.mqtt.CommandPublisher;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author datadev
 */
public class MockCommandPublisher implements CommandPublisher {

    /**
     * LOG
     */
    private final static Logger LOG = Logger.getLogger(MockCommandPublisher.class.getName());
    

    @Override
    public void sendMessage(String request) throws Exception {
        LOG.log(Level.INFO, "sending the request {0} to mqtt broker", request);
    }

}
