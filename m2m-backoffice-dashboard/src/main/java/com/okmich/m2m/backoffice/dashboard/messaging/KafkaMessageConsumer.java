/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.m2m.backoffice.dashboard.messaging;

import static com.okmich.m2m.backoffice.dashboard.OptionRegistry.*;
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

    private final KafkaConsumer<String, String> kafkaConsumer;
    private final ExecutorService executorService;

    /**
     *
     */
    public KafkaMessageConsumer() {

        Properties props = new Properties();
        props.put("bootstrap.servers", value(KAFKA_BROKER_URL));
        props.put("group.id", "test");
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("session.timeout.ms", "30000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        this.kafkaConsumer = new KafkaConsumer<>(props);
        this.kafkaConsumer.subscribe(Arrays.asList(value("")));

        this.executorService = Executors.newFixedThreadPool(valueAsInteger(EXECUTOR_THREADS));
    }

    /**
     *
     */
    public void start() {
        while (true) {
            ConsumerRecords<String, String> records = this.kafkaConsumer.poll(100);
            for (ConsumerRecord<String, String> record : records) {
                this.executorService.submit(() -> {
                });
            }
        }
    }
}
