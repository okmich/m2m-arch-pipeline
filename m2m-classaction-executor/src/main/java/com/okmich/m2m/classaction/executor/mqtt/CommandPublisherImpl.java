/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.m2m.classaction.executor.mqtt;

import static com.okmich.m2m.classaction.executor.OptionRegistry.*;
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
public class CommandPublisherImpl implements CommandPublisher {

    /**
     * TOPIC_ID
     */
    private static final String TOPIC_ID = value(MQTT_COMMAND_TOPIC);
    /**
     * mqttClient
     */
    private final MqttClient mqttClient;
    /**
     * LOG
     */
    private static final Logger LOG = Logger.getLogger(CommandPublisherImpl.class.getName());

    /**
     *
     * @throws IOException
     */
    public CommandPublisherImpl() throws IOException {
        try {
            this.mqttClient = new MqttClient(value(MQTT_SERVER), value(MQTT_SERVER_ID));
            this.mqttClient.connect();
        } catch (MqttException ex) {
            LOG.log(Level.SEVERE, null, ex);
            throw new IOException(ex.getMessage(), ex);
        }
    }

    /**
     *
     * @param request
     * @throws java.lang.Exception
     */
    @Override
    public void sendMessage(String request) throws Exception {
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
