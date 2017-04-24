/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.m2m.classaction.executor.kakfa;

import static com.okmich.m2m.classaction.executor.OptionRegistry.*;
import java.util.Properties;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

/**
 *
 * @author m.enudi
 */
public class KafkaMessageProducerImpl implements KafkaMessageProducer {

    private final KafkaProducer<String, String> kafkaProducer;
    /**
     * TOPIC_ID
     */
    private static final String TOPIC_ID = value(KAFKA_CLASSFIED_ACTION_TOPIC);

    public KafkaMessageProducerImpl() {
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

    @Override
    public void send(String message) {
        kafkaProducer.send(new ProducerRecord(TOPIC_ID, message));
    }
}
