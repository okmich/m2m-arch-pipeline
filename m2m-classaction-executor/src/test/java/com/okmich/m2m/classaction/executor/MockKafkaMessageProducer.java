/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.m2m.classaction.executor;

import com.okmich.m2m.classaction.executor.kakfa.KafkaMessageProducer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author datadev
 */
public class MockKafkaMessageProducer implements KafkaMessageProducer {

    /**
     * LOG
     */
    private final static Logger LOG = Logger.getLogger(MockKafkaMessageProducer.class.getName());

    public MockKafkaMessageProducer() {
    }

    @Override
    public void send(String message) {
        LOG.log(Level.INFO, "sending the message {0}", message);
    }
}
