/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.sensor.server.net.handler;

import com.okmich.sensor.server.db.SensorChainDAO;
import com.okmich.sensor.server.db.SensorHBaseRepo;
import com.okmich.sensor.server.db.SensorReadingHBaseRepo;
import com.okmich.sensor.server.db.cache.CacheService;
import com.okmich.sensor.server.messaging.KafkaMessageProducer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author m.enudi
 */
public class MQBrokerCommandRunner implements CommandRunner {

    /**
     * cacheService
     */
    private final CacheService cacheService;
    /**
     * kafkaMessageProducer
     */
    private final KafkaMessageProducer kafkaMessageProducer;
    /**
     * sensorChainDAO
     */
    private final SensorChainDAO sensorChainDAO;
    /**
     * sensorHBaseRepo
     */
    private final SensorHBaseRepo sensorHBaseRepo;
    /**
     * sensorReadingHBaseRepo
     */
    private final SensorReadingHBaseRepo sensorReadingHBaseRepo;

    private static final Logger LOG = Logger.getLogger("com.okmich.sensor.server.net.SocketCommandRunner");

    private static final String CMD_INIT = "init:";
    private static final String CMD_DATA = "data:";

    private final ExecutorService executorService;

    /**
     *
     * @param cacheService
     * @param kafkaMessageProducer
     * @param isensorHBaseRepo
     * @param sensorChainDAO
     * @param sensorReadingHBaseRepo
     */
    public MQBrokerCommandRunner(CacheService cacheService,
            KafkaMessageProducer kafkaMessageProducer,
            SensorHBaseRepo isensorHBaseRepo,
            SensorChainDAO sensorChainDAO,
            SensorReadingHBaseRepo sensorReadingHBaseRepo) {
        this.cacheService = cacheService;
        this.kafkaMessageProducer = kafkaMessageProducer;
        this.sensorChainDAO = sensorChainDAO;
        this.sensorHBaseRepo = isensorHBaseRepo;
        this.sensorReadingHBaseRepo = sensorReadingHBaseRepo;

        executorService = Executors.newFixedThreadPool(6);
    }

    @Override
    public void runCommand(final String command) throws CommandException {
        executorService.submit(new Runnable() {

            Handler handler;
            String request;

            @Override
            public void run() {
                LOG.log(Level.INFO, "request command  is {0}", command);
                if (command.startsWith(CMD_INIT)) {
                    handler = new ConnectionInitiationHandler(cacheService, sensorHBaseRepo);
                    request = command.substring(CMD_INIT.length());
                } else if (command.startsWith(CMD_DATA)) {
                    handler = new DataTransmissionRequestHandler(
                            cacheService,
                            kafkaMessageProducer,
                            sensorChainDAO,
                            sensorReadingHBaseRepo);
                    request = command.substring(CMD_DATA.length());
                } else {
                    LOG.log(Level.SEVERE, "unknown command - {0}", command);
                    return;
                }
                try {
                    String response = handler.handle(request);
                    LOG.log(Level.INFO, response);
                } catch (Exception e) {
                    LOG.log(Level.SEVERE, e.getMessage(), e);
                }
            }
        });
    }
}
