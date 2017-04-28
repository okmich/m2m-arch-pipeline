/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.m2m.backoffice.dashboard.messaging;

import static com.okmich.m2m.backoffice.dashboard.OptionRegistry.*;
import com.okmich.m2m.backoffice.dashboard.controllers.UIController;
import java.util.Arrays;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

/**
 *
 * @author m.enudi
 */
public class KafkaMessageConsumer {

    //list of all controllers 
    private UIController actionPanelController;
    private UIController connectedSensorPanelController;
    private UIController disconnectedSensorPanelController;
    private UIController eventPanelController;

    private final KafkaConsumer<String, String> kafkaConsumer;
    private final ExecutorService executorService;

    /**
     *
     */
    private KafkaMessageConsumer() {

        Properties props = new Properties();
        props.put("bootstrap.servers", value(KAFKA_BROKER_URL));
        props.put("group.id", "test");
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("session.timeout.ms", "30000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        this.kafkaConsumer = new KafkaConsumer<>(props);
        //load topics
        this.kafkaConsumer.subscribe(Arrays.asList(
                value(KAFKA_ACTION_LOG_TOPIC),
                value(KAFKA_ENRICHED_EVENT_TOPIC),
                value(KAFKA_LOSS_CONN_TOPIC),
                value(KAFKA_RAW_EVENT_TOPIC)));

        this.executorService = Executors.newFixedThreadPool(valueAsInteger(EXECUTOR_THREADS));
    }

    public KafkaMessageConsumer(UIController actionPanelController,
            UIController connectedSensorPanelController,
            UIController disconnectedSensorPanelController,
            UIController eventPanelController) {
        this();

        this.actionPanelController = actionPanelController;
        this.connectedSensorPanelController = connectedSensorPanelController;
        this.disconnectedSensorPanelController = disconnectedSensorPanelController;
        this.eventPanelController = eventPanelController;
    }

    /**
     *
     */
    public void start() {
        while (true) {
            ConsumerRecords<String, String> records = this.kafkaConsumer.poll(100);
            for (ConsumerRecord<String, String> record : records) {
                this.executorService.submit(() -> {
                    String topic = record.topic();
                    String payload = record.value();
                    switch (topic) {
                        case KAFKA_ACTION_LOG_TOPIC: //cmd;bsdevId;arg;ts
                            actionPanelController.process(payload);
                            break;
                        case KAFKA_ENRICHED_EVENT_TOPIC: //
                            eventPanelController.process(payload);
                            break;
                        case KAFKA_LOSS_CONN_TOPIC: //devId;ts
                            disconnectedSensorPanelController.process(payload);
                            break;
                        case KAFKA_RAW_EVENT_TOPIC: //devId;type;add;sDevId;dsbs;bsdev;lct;geo
                            connectedSensorPanelController.process(payload);
                            break;
                        default:
                    }
                });
            }
        }
    }
}
