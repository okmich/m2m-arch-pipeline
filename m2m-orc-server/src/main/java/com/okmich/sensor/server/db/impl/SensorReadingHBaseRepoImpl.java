/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.sensor.server.db.impl;

import static com.okmich.sensor.server.OptionRegistry.*;
import com.okmich.sensor.server.db.SensorReadingHBaseRepo;
import com.okmich.sensor.server.model.SensorReading;
import static com.okmich.sensor.server.util.Util.as;
import java.io.IOException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HConstants;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Table;

/**
 *
 * @author m.enudi
 */
public class SensorReadingHBaseRepoImpl implements SensorReadingHBaseRepo {

    private final Table table;

    /**
     * TABLE_SENSOR
     */
    public static final String TABLE_SENSOR_READING = "sensor_data";
    /**
     * TABLE_SENSOR
     */
    public static final byte[] TABLE_SENSOR_READING_BYTE = as(TABLE_SENSOR_READING);
    /**
     * COLUMN_FAMILY_RAW
     */
    public static final byte[] COLUMN_FAMILY_RAW = as("raw");
    /**
     * COLUMN_DEV_ID
     */
    public static final byte[] COLUMN_DEV_ID = as("devId");
    /**
     * COLUMN_TIMESTAMP
     */
    public static final byte[] COLUMN_TIMESTAMP = as("ts");
    /**
     * COLUMN_PRESSURE
     */
    public static final byte[] COLUMN_PRESSURE = as("prs");
    /**
     * COLUMN_TEMPERATURE
     */
    public static final byte[] COLUMN_TEMPERATURE = as("tmp");
    /**
     * COLUMN_VOLUME
     */
    public static final byte[] COLUMN_VOLUME = as("vol");
    /**
     * COLUMN_FLOW_VELOCITY
     */
    public static final byte[] COLUMN_FLOW_VELOCITY = as("flv");
    /**
     * COLUMN_EXTERNAL_BODY_FORCE
     */
    public static final byte[] COLUMN_EXTERNAL_BODY_FORCE = as("xbf");

    /**
     *
     * @throws java.io.IOException
     */
    public SensorReadingHBaseRepoImpl() throws IOException {
        Configuration conf = HBaseConfiguration.create();
        conf.set(HConstants.ZOOKEEPER_QUORUM, value(HBASE_ZOOKEEPER_QUORUM));
        conf.set(HConstants.ZOOKEEPER_CLIENT_PORT, value(HBASE_ZOOKEEPER_CLIENT_PORT));
        Connection connection = ConnectionFactory.createConnection(conf);
        this.table = connection.getTable(TableName.valueOf(TABLE_SENSOR_READING));
    }

    /**
     *
     * @param sensorReading
     * @throws Exception
     */
    @Override
    public void save(SensorReading sensorReading) throws Exception {
        long reverseTstamp = Long.MAX_VALUE - sensorReading.getTimestamp();
        Put put = new Put(as(sensorReading.getDevId() + "-" + reverseTstamp));

        put.addColumn(COLUMN_FAMILY_RAW, COLUMN_DEV_ID, as(sensorReading.getDevId()));
        put.addColumn(COLUMN_FAMILY_RAW, COLUMN_TIMESTAMP, as(sensorReading.getTimestamp()));
        put.addColumn(COLUMN_FAMILY_RAW, COLUMN_PRESSURE, as(sensorReading.getPressure()));
        put.addColumn(COLUMN_FAMILY_RAW, COLUMN_TEMPERATURE, as(sensorReading.getTemperature()));
        put.addColumn(COLUMN_FAMILY_RAW, COLUMN_VOLUME, as(sensorReading.getVolume()));
        put.addColumn(COLUMN_FAMILY_RAW, COLUMN_FLOW_VELOCITY, as(sensorReading.getFlowVelocity()));
        put.addColumn(COLUMN_FAMILY_RAW, COLUMN_EXTERNAL_BODY_FORCE, as(sensorReading.getExtBodyForce()));

        try {
            table.put(put);
        } catch (IOException e) {
            throw new Exception(e.getMessage(), e);
        }
    }

}
