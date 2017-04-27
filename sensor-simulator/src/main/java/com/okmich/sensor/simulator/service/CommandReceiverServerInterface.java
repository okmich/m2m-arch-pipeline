/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.sensor.simulator.service;

import static com.okmich.sensor.simulator.OptionRegistry.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import com.okmich.sensor.simulator.service.handler.DataHandler;

/**
 *
 * @author m.enudi
 */
public final class CommandReceiverServerInterface implements Runnable, MqttCallbackExtended {

    /**
     * mqttClient
     */
    private final MqttClient mqttClient;
    /**
     * TOPIC_ID
     */
    private static final String TOPIC_ID = value(MQTT_SERVER_COMMADN_RECEIPT_TOPIC) + "/";
    /**
     *
     */
    private final DataHandler handler;

    /**
     * LOG
     */
    private static final Logger LOG = Logger.getLogger(CommandReceiverServerInterface.class.getName());

    /**
     *
     * @param handler
     * @throws IOException
     */
    public CommandReceiverServerInterface(DataHandler handler) throws IOException {
        try {
            this.mqttClient = new MqttClient(mqttServer(), value(DEVICE_ID) + "-command");
        } catch (MqttException ex) {
            LOG.log(Level.SEVERE, null, ex);
            throw new IOException(ex.getMessage(), ex);
        }

        try {
            this.mqttClient.setCallback(this);
            this.mqttClient.connect();
            this.mqttClient.subscribe(TOPIC_ID + value(DEVICE_ID), 1);
        } catch (MqttException ex) {
            LOG.log(Level.SEVERE, null, ex);
        }
        this.handler = handler;
    }

    @Override
    public void connectionLost(Throwable thrwbl) {
        LOG.log(Level.INFO, "connection lost");
    }

    @Override
    public void messageArrived(String topic, MqttMessage mm) throws Exception {
        LOG.log(Level.SEVERE, "messageArrived on {0} saying {1}", new Object[]{topic, new String(mm.getPayload())});
        handler.handle(new String(mm.getPayload()));
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken imdt) {
        try {
            LOG.log(Level.SEVERE, "delivery complete on {0}", imdt.getMessage().getId());
        } catch (MqttException ex) {
            LOG.log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
    }

    @Override
    public void connectComplete(boolean reconnect, String serverURI) {
        LOG.log(Level.SEVERE, "connectComplete on {0} saying {1}", new Object[]{reconnect, serverURI});
    }

}
