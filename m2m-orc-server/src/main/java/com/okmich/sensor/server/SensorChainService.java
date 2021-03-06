/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.sensor.server;

import static com.okmich.sensor.server.OptionRegistry.KAFKA_LOST_CONN_TOPIC;
import static com.okmich.sensor.server.OptionRegistry.NO_DATA_THRESHHOLD;
import static com.okmich.sensor.server.OptionRegistry.value;
import static com.okmich.sensor.server.OptionRegistry.valueAsInteger;
import com.okmich.sensor.server.db.SensorChainDAO;
import com.okmich.sensor.server.db.cache.CacheService;
import com.okmich.sensor.server.messaging.KafkaMessageProducer;
import com.okmich.sensor.server.model.Sensor;
import com.okmich.sensor.server.model.SensorReading;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author m.enudi
 */
public class SensorChainService {

    private final CacheService cacheService;
    private final KafkaMessageProducer kafkaMessageProducer;
    private final SensorChainDAO sensorChainDAO;
    private final ScheduledExecutorService scheduledService;
    private final ExecutorService executorService;

    private final SensorChainRestorService sensorChainRestorService;
    /**
     * THRESHHOLD
     */
    private final static int THRESHHOLD = valueAsInteger(NO_DATA_THRESHHOLD);
    /**
     * LOG
     */
    private static final Logger LOG = Logger.getLogger(SensorChainService.class.getName());

    /**
     *
     * @param cacheService
     * @param kafkaMessageProducer
     * @param sensorChainDAO
     */
    public SensorChainService(CacheService cacheService,
            KafkaMessageProducer kafkaMessageProducer,
            SensorChainDAO sensorChainDAO) {
        this.cacheService = cacheService;
        this.kafkaMessageProducer = kafkaMessageProducer;
        this.sensorChainDAO = sensorChainDAO;

        scheduledService = Executors.newScheduledThreadPool(1);
        executorService = Executors.newFixedThreadPool(1);

        sensorChainRestorService = new SensorChainRestorService();
        //load all sensor chains
        this.sensorChainDAO.loadSensorChain(cacheService.getSensors());
    }

    /**
     *
     */
    public void start() {
        LOG.log(Level.INFO, "Starting the sensor chain maintenance service");
        scheduledService.scheduleWithFixedDelay(sensorChainUpdateService,
                2 * THRESHHOLD, THRESHHOLD, TimeUnit.SECONDS);
        LOG.log(Level.INFO, "Sensor chain maintenance service to start in {0} seconds", THRESHHOLD);
    }

    /**
     *
     */
    public void stop() {
        LOG.log(Level.INFO, "Sensor chain maintenance service shutting down");
        scheduledService.shutdown();
    }

    /**
     *
     * @param devId
     */
    public void restoreDevState(String devId) {
        LOG.log(Level.INFO, "Restoring devId {0} from the connection lost registry", devId);
        sensorChainRestorService.setDevId(devId);
        this.executorService.submit(sensorChainRestorService);
    }

    /**
     *
     */
    private final Runnable sensorChainUpdateService = new Runnable() {

        @Override
        public void run() {
            LOG.info(">>>>>>>>>>>> start running sensorChainUpdateService <<<<<<<<<<<<<<<<<");
            //get the list of all sensors
            List<Sensor> sensors;
            try {
                sensors = cacheService.getSensors();
            } catch (Exception ex) {
                LOG.log(Level.SEVERE, null, ex);
                return;
            }
            //for each sensor
            SensorReading sensorReading;
            String devId;
            for (Sensor sensor : sensors) {
                devId = sensor.getDevId();
                try {

                    //return the latest reading from cache
                    sensorReading = cacheService.getSensorReading(devId);
                    if (sensorReading == null || isStaleReading(sensorReading)) {
                        //if the last reading is mor than threshhold seconds ago
                        //send lost sensor connection message to kafka
                        kafkaMessageProducer.send(value(KAFKA_LOST_CONN_TOPIC), devId + ";" + System.currentTimeMillis());
                        //get the real time chain-post sensor for this sensor and make its 
                        //chain-pre sensor the real time chain-pre sensor for this sensor 
                        String fromDevId = sensorChainDAO.getFromDevID(devId);
                        if (fromDevId != null) {
                            sensorChainDAO.saveSensorChain(fromDevId,
                                    sensorChainDAO.getFromDevID(fromDevId),
                                    sensorChainDAO.getToDevID(devId));
                        }
                    }
                } catch (Exception ex) {
                    LOG.log(Level.SEVERE, ex.getMessage(), ex);
                }
                //if the last reading is withing the threshhold seconds, do nothing
            }
            LOG.info(">>>>>>>>>>> done running sensorChainUpdateService <<<<<<<<<<<<<<<<");
        }

        /**
         *
         * @param sensorReading
         * @return
         */
        private boolean isStaleReading(SensorReading sensorReading) {
            long diff = (System.currentTimeMillis() - sensorReading.getTimestamp()) / 1000;
            return diff >= THRESHHOLD;
        }

    };

    /**
     *
     * @author m.enudi
     */
    private class SensorChainRestorService implements Runnable {

        private String devId;

        @Override
        public void run() {
            //get the original positions
            String orgFromID = sensorChainDAO.getOrgFromDevID(devId);
            String orgToID = sensorChainDAO.getOrgToDevID(devId);
            //change the from 
            sensorChainDAO.saveSensorChain(orgFromID,
                    sensorChainDAO.getFromDevID(orgFromID),
                    devId);
            //change the to
            sensorChainDAO.saveSensorChain(orgToID,
                    devId,
                    sensorChainDAO.getToDevID(orgToID));
            //change dev
            sensorChainDAO.saveSensorChain(devId, orgFromID, orgToID);
        }

        /**
         * @param devId the devId to set
         */
        public void setDevId(String devId) {
            this.devId = devId;
        }
    }
}
