/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.sensor.server.net;

import com.okmich.sensor.server.net.handler.CommandRunner;
import static com.okmich.sensor.server.OptionRegistry.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 *
 * @author m.enudi
 */
public class ServerNetworkInterface implements MqttCallbackExtended {

    private CommandRunner commandRunner;

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
    private static final Logger LOG = Logger.getLogger(ServerNetworkInterface.class.getName());

    /**
     *
     * @throws IOException
     */
    public ServerNetworkInterface() throws IOException {
        try {
            this.mqttClient = new MqttClient(mqttServer(), value(MQTT_SERVER_ID));
        } catch (MqttException ex) {
            LOG.log(Level.SEVERE, null, ex);
            throw new IOException(ex.getMessage(), ex);
        }
        beginServices();
    }

    /**
     * @param commandRunner the commandRunner to set
     */
    public void setCommandRunner(CommandRunner commandRunner) {
        this.commandRunner = commandRunner;
    }

    /**
     *
     * @throws IOException
     */
    private void beginServices() throws IOException {
        try {
            this.mqttClient.connect();
            this.mqttClient.setCallback(this);
            this.mqttClient.subscribe(TOPIC_ID);
        } catch (MqttException ex) {
            LOG.log(Level.SEVERE, null, ex);
            throw new IOException(ex.getMessage(), ex);
        }
    }

    @Override
    public void connectionLost(Throwable thrwbl) {
        LOG.log(Level.SEVERE, "connection lost to server {0}", thrwbl.getMessage());
    }

    @Override
    public void messageArrived(String string, MqttMessage mm) throws Exception {
        LOG.log(Level.INFO, "message arrived : {0}", mm.getPayload());
        commandRunner.runCommand(new String(mm.getPayload()));
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken imdt) {
    }

    @Override
    public void connectComplete(boolean reconnect, String serverURI) {
        LOG.log(Level.INFO, "connection established with broker {0}. Is reconnect? {1}", new Object[]{serverURI, reconnect});
    }
}
