/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.m2m.classaction.executor;

import com.okmich.m2m.classaction.executor.db.CommandAuditRepo;
import com.okmich.m2m.classaction.executor.kakfa.KafkaMessageProducer;
import com.okmich.m2m.classaction.executor.mqtt.CommandPublisher;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author m.enudi
 */
public class RunnableMessageHandlerImpl implements Runnable, MessageHandler {

    /**
     * payload
     */
    private final String payload;
    /**
     * kafkaMessageProducer
     */
    private final CommandAuditRepo commandAuditRepo;
    /**
     * commandPublisher
     */
    private final CommandPublisher commandPublisher;
    /**
     * kafkaMessageProducer
     */
    private final KafkaMessageProducer kafkaMessageProducer;
    /**
     * LOG
     */
    private final static Logger LOG = Logger.getLogger(RunnableMessageHandlerImpl.class.getName());

    /**
     *
     * @param arg
     * @param commandPublisher
     * @param kafkaMessageProducer
     * @param commandAuditRepo
     */
    public RunnableMessageHandlerImpl(String arg,
            CommandPublisher commandPublisher,
            KafkaMessageProducer kafkaMessageProducer,
            CommandAuditRepo commandAuditRepo) {
        this.payload = arg;
        this.commandPublisher = commandPublisher;
        this.kafkaMessageProducer = kafkaMessageProducer;
        this.commandAuditRepo = commandAuditRepo;
    }

    @Override
    public void run() {
        this.handle(payload);
    }

    @Override
    public void handle(String payload) {
        //split payloads
        String[] parts = payload.split(";");
        //do the whole processing as thus
        //extract the clz 
        String cmdKey = parts[15];
        //lookup the command from the clz
        String cmd = CommandRegistry.getCommand(cmdKey);
        if (cmd != null && !cmd.isEmpty()) {
            try {
                //log your command to kakfa
                kafkaMessageProducer.send(createKafkaMessage(cmd, parts));
                //implement command execution
                this.commandPublisher.sendMessage(cmd);
                //save to db
                this.commandAuditRepo.saveCommand(parts);
            } catch (Exception ex) {
                LOG.log(Level.SEVERE, null, ex);
            }

        }
    }

    /**
     *
     * @param cmd
     * @return
     */
    private String createKafkaMessage(String cmd, String parts[]) {
        return cmd;
    }

}
