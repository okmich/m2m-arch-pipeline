/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.sensor.simulator.net;

import static com.okmich.sensor.simulator.OptionRegistry.*;
import com.okmich.sensor.simulator.model.Reading;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 *
 * @author m.enudi
 */
public class NetworkInterface {

    private static final String CMD_INIT = "init:";
    private static final String CMD_DATA = "data:";

    /**
     * TOPIC_ID
     */
    private static final String TOPIC_ID = value(MQTT_SERVER_DATA_SUBMIT_TOPIC);
    /**
     * mqttClient
     */
    private final MqttClient mqttClient;
    /**
     * LOG
     */
    private static final Logger LOG = Logger.getLogger(NetworkInterface.class.getName());

    /**
     *
     * @throws IOException
     */
    public NetworkInterface() throws IOException {
        try {
            this.mqttClient = new MqttClient(mqttServer(), value(DEVICE_ID));
            this.mqttClient.connect();
        } catch (MqttException ex) {
            LOG.log(Level.SEVERE, null, ex);
            throw new IOException(ex.getMessage(), ex);
        }
    }

    /**
     *
     * @param devId
     * @throws IOException
     * @throws IllegalStateException
     */
    public void initConnectionToServer(String devId) throws Exception {
        //devId;type;add;dsbs;bsdev;geo
        String cmd = CMD_INIT + value(DEVICE_ID)
                + ";" + value(TYPE)
                + ";" + value(ADDRESS)
                + ";" + value(DIST_SUPPLY_DEV)
                + ";" + value(BS_DEV_ID)
                + ";" + value(GEO);
        sendMessage(cmd);
    }

    /**
     *
     * @param sensordata
     * @throws java.lang.Exception
     */
    public void transferDataToServer(Reading sensordata) throws Exception {
        sendMessage(CMD_DATA + sensordata.toString());
    }

    /**
     *
     * @param future
     * @return
     */
    private void sendMessage(String request) throws Exception {
        LOG.log(Level.INFO, "sending message {0}", request);
        MqttMessage mqttMessage = new MqttMessage(request.getBytes());
        mqttMessage.setQos(1);
        mqttMessage.setRetained(true);
        try {
            mqttClient.publish(TOPIC_ID, mqttMessage);
        } catch (MqttException ex) {
            throw new Exception(ex);
        }
    }

}
