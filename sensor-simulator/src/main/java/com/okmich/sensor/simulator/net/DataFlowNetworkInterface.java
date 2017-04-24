/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.sensor.simulator.net;

import static com.okmich.sensor.simulator.OptionRegistry.*;
import java.io.IOException;
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
    private static final String TOPIC_ID_2 = value(MQTT_SERVER_DATAFLOW_RESPONSE_TOPIC);
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
    private final DataFlowHandler handler;

    /**
     *
     * @param dfHandler
     * @throws IOException
     */
    public DataFlowNetworkInterface(DataFlowHandler dfHandler) throws IOException {
        try {
            this.mqttClient = new MqttClient(mqttServer(), value(DEVICE_ID) + "-dataFlow");
            this.mqttClient.connect();
            this.mqttClient.setCallback(this);
            this.mqttClient.subscribe(TOPIC_ID_2);
        } catch (MqttException ex) {
            LOG.log(Level.SEVERE, null, ex);
            throw new IOException(ex.getMessage(), ex);
        }
        this.handler = dfHandler;
    }

    /**
     *
     * @param devId
     * @throws java.lang.Exception
     */
    public void requestData(String devId) throws Exception {
        MqttMessage mqttMessage = new MqttMessage((CMD_FLOW + "devId:" + devId).getBytes());
        mqttMessage.setQos(0);
        try {
            mqttClient.publish(TOPIC_ID_1, mqttMessage);
        } catch (MqttException ex) {
            LOG.log(Level.SEVERE, null, ex);
            throw new Exception(ex.getMessage(), ex);
        }
    }

    @Override
    public void connectionLost(Throwable thrwbl) {

    }

    @Override
    public void messageArrived(String string, MqttMessage mm) throws Exception {
        this.handler.handleDataFlowResponse(new String(mm.getPayload()));
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken imdt) {

    }

}
