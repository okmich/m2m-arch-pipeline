/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.m2m.classaction.executor;

import com.okmich.m2m.classaction.executor.db.CacheService;
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
     * commandAuditRepo
     */
    private final CommandAuditRepo commandAuditRepo;
    /**
     * cacheService
     */
    private final CacheService cacheService;
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
     * @param cacheService
     * @param commandPublisher
     * @param kafkaMessageProducer
     * @param commandAuditRepo
     */
    public RunnableMessageHandlerImpl(String arg,
            CacheService cacheService,
            CommandPublisher commandPublisher,
            KafkaMessageProducer kafkaMessageProducer,
            CommandAuditRepo commandAuditRepo) {
        this.payload = arg;
        this.cacheService = cacheService;
        this.commandPublisher = commandPublisher;
        this.kafkaMessageProducer = kafkaMessageProducer;
        this.commandAuditRepo = commandAuditRepo;
    }

    @Override
    public void run() {
        LOG.log(Level.INFO, "processing {0}", payload);
        this.handle(payload);
        LOG.log(Level.INFO, "done processing {0}", payload);
    }

    @Override
    public void handle(String payload) {
        //split payloads
        String[] parts = payload.split(";");
        if (parts.length < 17) {
            LOG.log(Level.SEVERE, "{0} is an invalid payload for processing", payload);
            return;
        }
        //do the whole processing as thus
        //extract the clz 
        String clz = parts[16];
        //lookup the command from the clz
        String cmd = CommandRegistry.getCommand(clz);
        LOG.log(Level.INFO, "sending comming {0}", cmd);
        if (cmd != null && !cmd.isEmpty()) {
            try {
                String command = createSensorCommand(cmd, parts);
                //log your command to kakfa
                kafkaMessageProducer.send(command);
                //implement command execution
                this.commandPublisher.sendMessage(parts[7], command);
                //save to db
                this.commandAuditRepo.saveCommand(command, parts);
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
    private String createSensorCommand(String cmd, String parts[]) {
        //command= cmd;bsdevId;arg;ts
        String baseDevId = cacheService.getSensorSupplyBaseDeviceId(parts[7]);
        return String.format("%s;%s;%s;%d", cmd, baseDevId, parts[16], System.currentTimeMillis());
    }

}
