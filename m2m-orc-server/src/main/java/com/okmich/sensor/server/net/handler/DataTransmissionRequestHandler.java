/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.sensor.server.net.handler;

import static com.okmich.sensor.server.OptionRegistry.*;
import com.okmich.sensor.server.db.CacheService;
import com.okmich.sensor.server.db.SensorChainDAO;
import com.okmich.sensor.server.db.SensorReadingHBaseRepo;
import com.okmich.sensor.server.messaging.KafkaMessageProducer;
import com.okmich.sensor.server.model.Sensor;
import com.okmich.sensor.server.model.SensorReading;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author m.enudi
 */
public class DataTransmissionRequestHandler extends Handler {

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
     * sensorReadingHBaseRepo
     */
    private final SensorReadingHBaseRepo sensorReadingHBaseRepo;

    /**
     *
     */
    private static final Logger LOG = Logger.getLogger(DataTransmissionRequestHandler.class.getName());

    /**
     *
     * @param cacheService
     * @param kafkaMessageProducer
     * @param sensorChainDAO
     * @param sensorReadingHBaseRepo
     */
    public DataTransmissionRequestHandler(
            CacheService cacheService,
            KafkaMessageProducer kafkaMessageProducer,
            SensorChainDAO sensorChainDAO,
            SensorReadingHBaseRepo sensorReadingHBaseRepo) {
        this.cacheService = cacheService;
        this.kafkaMessageProducer = kafkaMessageProducer;
        this.sensorChainDAO = sensorChainDAO;
        this.sensorReadingHBaseRepo = sensorReadingHBaseRepo;
    }

    @Override
    public String handle(String request) {
        //get the devId from the request
        String devId = getDeviceId(request);
        if (devId == null) {
            throw new IllegalArgumentException("no devId in request");
        }
        SensorReading sensorReading = new SensorReading(request);
        try {
            //cache the latest reading
            cacheService.saveSensorReading(sensorReading);
            //enrich it with latest reading for the pre-chain sensor
            String enrichedMsg = enrichMessage(sensorReading);
            //send enrich data to kafka
            kafkaMessageProducer.send(value(KAFKA_ENRICHED_MESSAGE_TOPIC), enrichedMsg);
            //save reading to hbase
            sensorReadingHBaseRepo.save(sensorReading);
            return "received";
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     *
     * @param request
     * @return
     */
    private String getDeviceId(String request) {
        String[] fields = request.split(";");

        if (!fields[0].isEmpty()) {
            return fields[0];
        }
        return null;
    }

    /**
     *
     * @param sensorReading
     * @return
     */
    private String enrichMessage(SensorReading sensorReading) {
        //
        String suppDevId = this.sensorChainDAO.getFromDevID(sensorReading.getDevId());
        if (suppDevId == null || suppDevId.isEmpty()) {
            //there is not supplying dev and the distant should be zero
            return "|" + sensorReading.toString() + "|" + 0;
        } else {
            SensorReading sensorReadingSuppDev = cacheService.getSensorReading(suppDevId);
            if (sensorReadingSuppDev == null) {
                throw new IllegalArgumentException("");
            }
            float dist = getDistanceBetweenSensors(suppDevId, sensorReading.getDevId());
            return sensorReadingSuppDev.toString() + "|" + sensorReading.toString() + "|dist:" + dist;
        }
    }

    /**
     *
     * @param suppDevId
     * @param devId
     * @return
     */
    private float getDistanceBetweenSensors(String suppDevId, String devId) {
        Sensor first = cacheService.getSensor(suppDevId);
        Sensor second = cacheService.getSensor(devId);
        return Math.abs(first.getDistSupplyDev() - second.getDistSupplyDev());
    }
}
