/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.sensor.server;

import com.okmich.sensor.server.db.CacheService;
import com.okmich.sensor.server.db.SensorChainDAO;
import com.okmich.sensor.server.net.DataFlowNetworkInterface;
import com.okmich.sensor.server.net.handler.MQBrokerCommandRunner;
import com.okmich.sensor.server.net.ServerNetworkInterface;
import com.okmich.sensor.server.net.handler.DataflowRequestHandler;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.okmich.sensor.server.db.SensorHBaseRepo;
import com.okmich.sensor.server.db.SensorReadingHBaseRepo;
import com.okmich.sensor.server.db.impl.CacheServiceImpl;
import com.okmich.sensor.server.db.impl.SensorChainDAOImpl;
import com.okmich.sensor.server.db.impl.SensorHBaseRepoImpl;
import com.okmich.sensor.server.db.impl.SensorReadingHBaseRepoImpl;
import com.okmich.sensor.server.messaging.KafkaMessageProducer;
import com.okmich.sensor.server.model.Sensor;
import java.util.List;

/**
 *
 * @author m.enudi
 */
public class ServerMain {

    /**
     *
     */
    private static final Logger LOG = Logger.getLogger(ServerMain.class.getName());
    /**
     *
     */
    private SensorChainService sensorChainService;

    private ServerMain() throws IOException {
        bootstrapObjects();
    }

    public static void main(String[] args) throws IOException {
        //for dev purpose, initialize the OptionRegistry
        LOG.log(Level.INFO, "initializing configuration variables");
        OptionRegistry.initialize(args);

        try {

            ServerMain main = new ServerMain();
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, null, ex);
        }
    }

    private void bootstrapObjects() throws IOException {
        LOG.log(Level.INFO, "boostrapping all application service objects");
        //data access objects

        CacheService cacheService = new CacheServiceImpl();
        KafkaMessageProducer kafkaMessageProducer = new KafkaMessageProducer();
        SensorHBaseRepo sensorHBaseRepo = new SensorHBaseRepoImpl();
        SensorReadingHBaseRepo sensorReadingHBaseRepo = new SensorReadingHBaseRepoImpl();

        SensorChainDAO sensorChainDAO = new SensorChainDAOImpl();

        LOG.log(Level.INFO, "refreshing the cache with all sensor records");
        cacheSensors(sensorHBaseRepo, cacheService);

        ServerNetworkInterface serverNetworkInterface = new ServerNetworkInterface();
        LOG.log(Level.INFO, "starting the server interface");
        serverNetworkInterface.setCommandRunner(
                new MQBrokerCommandRunner(cacheService,
                        kafkaMessageProducer,
                        sensorHBaseRepo,
                        sensorChainDAO,
                        sensorReadingHBaseRepo));
        LOG.log(Level.INFO, "ready to service client request");

        LOG.log(Level.INFO, "starting the data flow simulating service");
        DataFlowNetworkInterface dataFlowNetworkInterface
                = new DataFlowNetworkInterface(new DataflowRequestHandler(cacheService, sensorChainDAO));

        LOG.log(Level.INFO, "initiating the sensor chain service");
        sensorChainService = new SensorChainService(cacheService, kafkaMessageProducer, sensorChainDAO);
        LOG.log(Level.INFO, "starting the sensor chain service");
        sensorChainService.start();
    }

    /**
     *
     * @param sensorHBaseRepo
     * @param cacheService
     */
    private void cacheSensors(SensorHBaseRepo sensorHBaseRepo, CacheService cacheService) {
        List<Sensor> sensors = sensorHBaseRepo.findAll();
        cacheService.saveSensors(sensors);
        LOG.log(Level.INFO, "{0} sensors loaded to cache", sensors.size());
    }
}
