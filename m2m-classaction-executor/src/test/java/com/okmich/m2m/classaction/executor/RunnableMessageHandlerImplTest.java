/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.m2m.classaction.executor;

import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author datadev
 */
public class RunnableMessageHandlerImplTest {

    private MessageHandler messageHandler;

    @Before
    public void init() {
        this.messageHandler = new RunnableMessageHandlerImpl(
                "TEST_COMMAND;0", new MockCommandPublisher(),
                new MockKafkaMessageProducer(),
                new MockCommandAuditRepoImpl());
    }

    @Test
    public void testHandleCommand() {
        this.messageHandler.handle("0001;435820349852345;23.20;21.29;"
                + "100;10.10;4.30;0001;435820349852345;23.20;21.29;"
                + "100;10.10;4.30;120;NOR;0");
    }

}
