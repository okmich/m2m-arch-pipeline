/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.m2m.backoffice.dashboard.messaging;

import static com.okmich.m2m.backoffice.dashboard.OptionRegistry.*;
import com.okmich.m2m.backoffice.dashboard.controllers.UIController;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.util.Arrays;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    private UIController sensorPanelController;
    private UIController disconnectedPanelController;
    private UIController eventPanelController;
    private UIController consoleController;

    private final KafkaConsumer<String, String> kafkaConsumer;
    private final ExecutorService executorService;
    /**
     *
     */
    private static final Logger LOG = Logger.getLogger(KafkaMessageConsumer.class.getName());

    private static final String TOPIC_ACTION_LOG = value(KAFKA_ACTION_LOG_TOPIC);
    private static final String TOPIC_CLASSIFIED_EVENT_LOG = value(KAFKA_CLASSIFIED_EVENT_TOPIC);
    private static final String TOPIC_LOSS_CONN_LOG = value(KAFKA_LOSS_CONN_TOPIC);
    private static final String TOPIC_ENRICHED_EVENT_LOG = value(KAFKA_ENRICHED_EVENT_TOPIC);

    /**
     *
     */
    private KafkaMessageConsumer() {

        Properties props = new Properties();
        props.put("bootstrap.servers", value(KAFKA_BROKER_URL));
        props.put("group.id", "kafka-consumer-frontend");
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("session.timeout.ms", "30000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        this.kafkaConsumer = new KafkaConsumer<>(props);
        //load topics
        this.kafkaConsumer.subscribe(Arrays.asList(
                TOPIC_ENRICHED_EVENT_LOG,
                TOPIC_CLASSIFIED_EVENT_LOG,
                TOPIC_ACTION_LOG,
                TOPIC_LOSS_CONN_LOG));

        this.executorService = Executors.newFixedThreadPool(valueAsInteger(EXECUTOR_THREADS));
    }

    public KafkaMessageConsumer(UIController actionPanelController,
            UIController sensorPanelController,
            UIController disconnectedPanelController,
            UIController eventPanelController,
            UIController consoleController) {
        this();

        this.actionPanelController = actionPanelController;
        this.sensorPanelController = sensorPanelController;
        this.eventPanelController = eventPanelController;
        this.disconnectedPanelController = disconnectedPanelController;
        this.consoleController = consoleController;
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

                    if (topic.equals(TOPIC_ACTION_LOG)) {
                        //cmd;bsdevId;arg;ts
                        actionPanelController.process(payload);
                    } else if (topic.equals(TOPIC_CLASSIFIED_EVENT_LOG)) {
                        //
                        eventPanelController.process(payload);
                    } else if (topic.equals(KAFKA_LOSS_CONN_TOPIC)) {
                        //devId;ts
                        disconnectedPanelController.process(payload);
                    } else if (topic.equals(TOPIC_ENRICHED_EVENT_LOG)) {
                        //devId;ts;prs;tmp;vol;flv;xbf|devId;ts;prs;tmp;vol;flv;xbf|dist
                        sensorPanelController.process(payload);
                    }
                    consoleController.process(payload);
                    LOG.log(Level.INFO, "message received on {0}", topic);
                });
            }
        }
    }
}
