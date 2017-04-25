/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.m2m.classaction.executor.db;

import static com.okmich.m2m.classaction.executor.db.Util.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Table;

/**
 *
 * @author m.enudi
 */
public class CommandAuditHBaseRepoImpl implements CommandAuditRepo {

    private final Table table;

    /**
     * TABLE_SENSOR
     */
    public static final byte[] TABLE_SENSOR = as("action_log");
    /**
     * COLUMN_FAMILY_MAIN
     */
    public static final byte[] COLUMN_FAMILY_MAIN = as("main");
    /**
     * COLUMN_CONTROL_DEV_ID
     */
    public static final byte[] COLUMN_CONTROL_DEV_ID = as("ctrl_dev_id");
    /**
     * COLUMN_TIMESTAMP
     */
    public static final byte[] COLUMN_TIMESTAMP = as("ts");
    /**
     * COLUMN_COMMAND
     */
    public static final byte[] COLUMN_COMMAND = as("cmd");
    /**
     * COLUMN_ARG
     */
    public static final byte[] COLUMN_ARG = as("arg");
    /**
     * COLUMN_SOURCE_DEV_ID
     */
    public static final byte[] COLUMN_SOURCE_DEV_ID = as("src_dev_id");
    /**
     * COLUMN_ML_CLASS
     */
    public static final byte[] COLUMN_ML_CLASS = as("ml_class");

    /**
     * LOG
     */
    private static final Logger LOG = Logger.getLogger(CommandAuditHBaseRepoImpl.class.getName());

    /**
     *
     * @throws IOException
     */
    public CommandAuditHBaseRepoImpl() throws IOException {
        Configuration conf = HBaseConfiguration.create();
        Connection connection = ConnectionFactory.createConnection(conf);
        this.table = connection.getTable(TableName.valueOf("sensor_reading"));
    }

    @Override
    public void saveCommand(String command, String[] payload) {
        //command= cmd,bsdevId,arg,ts
        String[] cmdParts = command.split("\\|");
        long ts = Long.valueOf(cmdParts[3]);
        Put put = new Put(as(cmdParts[1] + ":" + (Long.MAX_VALUE - ts)));

        put.addColumn(COLUMN_FAMILY_MAIN, COLUMN_CONTROL_DEV_ID, as(cmdParts[1]));
        put.addColumn(COLUMN_FAMILY_MAIN, COLUMN_TIMESTAMP, as(ts));
        put.addColumn(COLUMN_FAMILY_MAIN, COLUMN_COMMAND, as(cmdParts[0]));
        put.addColumn(COLUMN_FAMILY_MAIN, COLUMN_ARG, as(cmdParts[2])); //argument to command
        put.addColumn(COLUMN_FAMILY_MAIN, COLUMN_SOURCE_DEV_ID, as(payload[7])); //generating sensor
        put.addColumn(COLUMN_FAMILY_MAIN, COLUMN_ML_CLASS, as(payload[15])); //classification 
        try {
            table.put(put);
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, null, ex);
        }
    }
}
