/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.sensor.server.db.impl;

import com.okmich.sensor.server.db.SensorHBaseRepo;
import com.okmich.sensor.server.model.Sensor;
import static com.okmich.sensor.server.util.Util.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;

/**
 *
 * @author m.enudi
 */
public class SensorHBaseRepoImpl implements SensorHBaseRepo {

    private final Table table;

    /**
     * TABLE_SENSOR
     */
    public static final byte[] TABLE_SENSOR = as("sensor");
    /**
     * COLUMN_FAMILY_MAIN
     */
    public static final byte[] COLUMN_FAMILY_MAIN = as("main");
    /**
     * COLUMN_DEV_ID
     */
    public static final byte[] COLUMN_DEV_ID = as("devId");
    /**
     * COLUMN_DEV_TYPE
     */
    public static final byte[] COLUMN_DEV_TYPE = as("type");
    /**
     * COLUMN_DEV_ADDRESS
     */
    public static final byte[] COLUMN_DEV_ADDRESS = as("add");
    /**
     * COLUMN_BS_SUPPLY_DEV_ID
     */
    public static final byte[] COLUMN_BS_SUPPLY_DEV_ID = as("bsdevId");
    /**
     * COLUMN_DIST_SUPPLY
     */
    public static final byte[] COLUMN_DIST_SUPPLY_STN = as("dstSuppStn");

    /**
     *
     * @throws IOException
     */
    public SensorHBaseRepoImpl() throws IOException {
        Configuration conf = HBaseConfiguration.create();
        Connection connection = ConnectionFactory.createConnection(conf);
        this.table = connection.getTable(TableName.valueOf("sensor_reading"));
    }

    /**
     *
     * @param sensor
     * @throws Exception
     */
    @Override
    public void save(Sensor sensor) throws Exception {
        Put put = new Put(as(sensor.getDevId()));

        put.addColumn(COLUMN_FAMILY_MAIN, COLUMN_DEV_TYPE, as(sensor.getType()));
        put.addColumn(COLUMN_FAMILY_MAIN, COLUMN_DEV_ADDRESS, as(sensor.getAddress()));
        put.addColumn(COLUMN_FAMILY_MAIN, COLUMN_BS_SUPPLY_DEV_ID, as(sensor.getBaseStationDevId()));
        put.addColumn(COLUMN_FAMILY_MAIN, COLUMN_DIST_SUPPLY_STN, as(sensor.getDistSupplyDev()));

        try {
            table.put(put);
        } catch (IOException ex) {
            throw new Exception(ex.getMessage(), ex);
        }
    }

    /**
     *
     * @return @throws java.lang.Exception
     */
    @Override
    public List<Sensor> findAll() throws Exception {
        Scan scan = new Scan();
        scan.addFamily(COLUMN_FAMILY_MAIN);

        try (ResultScanner resultScanner = table.getScanner(scan);) {
            List<Sensor> sensors = new ArrayList<>();
            Sensor sensor;
            Result result = resultScanner.next();
            while (result != null) {
                sensor = new Sensor();
                sensor.setAddress(getString(result.getValue(COLUMN_FAMILY_MAIN, COLUMN_DEV_ADDRESS)));
                sensor.setBaseStationDevId(getString(result.getValue(COLUMN_FAMILY_MAIN, COLUMN_BS_SUPPLY_DEV_ID)));
                sensor.setDevId(getString(result.getValue(COLUMN_FAMILY_MAIN, COLUMN_DEV_ID)));
                sensor.setDistSupplyDev(getFloat(result.getValue(COLUMN_FAMILY_MAIN, COLUMN_DIST_SUPPLY_STN)));
                sensor.setType(getString(result.getValue(COLUMN_FAMILY_MAIN, COLUMN_DEV_TYPE)));

                result = resultScanner.next();
            }
            return sensors;
        } catch (IOException ex) {
            Logger.getLogger(SensorHBaseRepoImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new Exception(ex.getMessage(), ex);
        }

    }

}
