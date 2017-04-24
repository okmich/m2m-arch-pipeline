/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.sensor.server.messaging;

import static com.okmich.sensor.server.OptionRegistry.*;
import java.util.Properties;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

/**
 *
 * @author m.enudi
 */
public class KafkaMessageProducer {

    private final KafkaProducer<String, String> kafkaProducer;

    public KafkaMessageProducer() {
        Properties properties = new Properties();
        properties.put("bootstrap.servers", value(KAFKA_BROKER_URL));
        properties.put("acks", "all");
        properties.put("retries", 0);
        properties.put("batch.size", 16384);
        properties.put("linger.ms", 1);
        properties.put("buffer.memory", 33554432);
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        kafkaProducer = new KafkaProducer<>(properties);
    }

    public void send(String topic, String message) {
        kafkaProducer.send(new ProducerRecord(topic, message));
    }
}
