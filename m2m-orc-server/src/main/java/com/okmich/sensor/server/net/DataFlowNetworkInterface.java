/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.sensor.server.net;

import static com.okmich.sensor.server.OptionRegistry.*;
import com.okmich.sensor.server.net.handler.Handler;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 *
 * @author m.enudi
 */
public class DataFlowNetworkInterface implements MqttCallback {

    private static final String CMD_FLOW = "flow:";
    /**
     * TOPIC_ID_1 - psensor_data_flow_request_topic_5XB90
     */
    private static final String TOPIC_ID_1 = value(MQTT_SERVER_DATAFLOW_REQUEST_TOPIC);
    /**
     * TOPIC_ID_2 - psensor_data_flow_response_topic_5X1JL
     */
    private static final String TOPIC_ID_2 = value(MQTT_SERVER_DATAFLOW_RESPONSE_TOPIC) + "/";
    /**
     * mqttClient
     */
    private final MqttClient mqttClient;
    /**
     * LOG
     */
    private static final Logger LOG = Logger.getLogger(DataFlowNetworkInterface.class.getName());
    /**
     *
     */
    private final Handler handler;

    /**
     * executorService
     */
    private final ExecutorService executorService;

    /**
     *
     * @param dfHandler
     * @throws IOException
     */
    public DataFlowNetworkInterface(Handler dfHandler) throws IOException {
        try {
            this.mqttClient = new MqttClient(mqttServer(), "DataFlow-aggServer" + System.currentTimeMillis());
            this.mqttClient.connect();
            this.mqttClient.setCallback(this);
            this.mqttClient.subscribe(TOPIC_ID_1);
        } catch (MqttException ex) {
            LOG.log(Level.SEVERE, null, ex);
            throw new IOException(ex.getMessage(), ex);
        }
        this.handler = dfHandler;
        executorService = Executors.newFixedThreadPool(valueAsInteger(DATAFLOW_REQUEST_HANDLER_THREADS));
    }

    /**
     *
     * @param devId
     * @param response
     * @throws java.lang.Exception
     */
    public void sendMessage(String devId, String response) throws Exception {
        MqttMessage mqttMessage = new MqttMessage(response.getBytes());
        mqttMessage.setQos(1);
        try {
            mqttClient.publish(TOPIC_ID_2 + devId, mqttMessage);
        } catch (MqttException ex) {
            LOG.log(Level.SEVERE, null, ex);
            throw new Exception(ex.getMessage(), ex);
        }
    }

    @Override
    public void connectionLost(Throwable thrwbl) {
        LOG.log(Level.SEVERE, "connection lost to server {0}", thrwbl.getMessage());
    }

    @Override
    public void messageArrived(String string, MqttMessage mm) throws Exception {
        final String message = new String(mm.getPayload());
        LOG.log(Level.INFO, "message arrived : {0}", message);
        executorService.submit(() -> {
            try {
                String devId = message.substring(CMD_FLOW.length());
                sendMessage(devId, handler.handle(devId));
            } catch (Exception ex) {
                LOG.log(Level.SEVERE, null, ex);
            }
        });
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken imdt) {

    }
}
