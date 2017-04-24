/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.sensor.server;

import com.okmich.sensor.server.db.CacheService;
import com.okmich.sensor.server.db.DBFactory;
import com.okmich.sensor.server.db.SensorChainDAO;
import com.okmich.sensor.server.net.DataFlowNetworkInterface;
import com.okmich.sensor.server.net.MQBrokerCommandRunner;
import com.okmich.sensor.server.net.ServerNetworkInterface;
import com.okmich.sensor.server.net.handler.DataflowRequestHandler;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.okmich.sensor.server.db.SensorDAO;
import com.okmich.sensor.server.db.SensorHBaseRepo;
import com.okmich.sensor.server.db.SensorReadingDAO;
import com.okmich.sensor.server.db.SensorReadingHBaseRepo;
import com.okmich.sensor.server.db.impl.CacheServiceImpl;
import com.okmich.sensor.server.db.impl.SensorHBaseRepoImpl;
import com.okmich.sensor.server.db.impl.SensorReadingHBaseRepoImpl;
import com.okmich.sensor.server.messaging.KafkaMessageProducer;

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
        Properties properties = new Properties();
        properties.load(ClassLoader.getSystemResourceAsStream("config.properties"));
        properties.list(System.out);

        OptionRegistry.initialize(properties);
        DBFactory.initialize();
        try {
            ServerMain main = new ServerMain();
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, null, ex);
        }
    }

    private void bootstrapObjects() throws IOException {
        //data access objects
        SensorDAO nodeDao = DBFactory.getSensorDao();
        SensorChainDAO sensorChainDAO = DBFactory.getSensorChainDAO();
        SensorReadingDAO nodeDataDao = DBFactory.getSensorDataDao();

        CacheService cacheService = new CacheServiceImpl();
        KafkaMessageProducer kafkaMessageProducer = new KafkaMessageProducer();
        SensorHBaseRepo sensorHBaseRepo = new SensorHBaseRepoImpl();
        SensorReadingHBaseRepo sensorReadingHBaseRepo = new SensorReadingHBaseRepoImpl();

        ServerNetworkInterface serverNetworkInterface = new ServerNetworkInterface();
        serverNetworkInterface.setCommandRunner(
                new MQBrokerCommandRunner(cacheService, kafkaMessageProducer, sensorReadingHBaseRepo));

        DataFlowNetworkInterface dataFlowNetworkInterface
                = new DataFlowNetworkInterface(new DataflowRequestHandler(nodeDao, sensorChainDAO, nodeDataDao));

    }
}
