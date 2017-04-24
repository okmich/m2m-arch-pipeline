/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.m2m.classaction.executor.kakfa;

import com.okmich.m2m.classaction.executor.RunnableMessageHandlerImpl;
import static com.okmich.m2m.classaction.executor.OptionRegistry.*;
import com.okmich.m2m.classaction.executor.db.CommandAuditRepo;
import com.okmich.m2m.classaction.executor.mqtt.CommandPublisher;
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

    private final CommandPublisher commandPublisher;

    private final KafkaMessageProducer kafkaMessageProducer;

    private final CommandAuditRepo commandAuditRepo;

    /**
     * 
     * @param icommandPublisher
     * @param ikafkaMessageProducer
     * @param icommandAuditRepo 
     */
    public KafkaMessageConsumer(CommandPublisher icommandPublisher,
            KafkaMessageProducer ikafkaMessageProducer,
            CommandAuditRepo icommandAuditRepo) {

        Properties props = new Properties();
        props.put("bootstrap.servers", value(KAFKA_BROKER_URL));
        props.put("group.id", "test");
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("session.timeout.ms", "30000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        this.kafkaConsumer = new KafkaConsumer<>(props);
        this.kafkaConsumer.subscribe(Arrays.asList(value(KAFKA_CLASSFIED_MSG_TOPIC)));

        this.executorService = Executors.newFixedThreadPool(valueAsInteger(EXECUTOR_THREADS));
        this.commandPublisher = icommandPublisher;
        this.kafkaMessageProducer = ikafkaMessageProducer;
        this.commandAuditRepo = icommandAuditRepo;
    }

    /**
     *
     */
    public void start() {
        while (true) {
            ConsumerRecords<String, String> records = this.kafkaConsumer.poll(100);
            for (ConsumerRecord<String, String> record : records) {
                this.executorService.submit(
                        new RunnableMessageHandlerImpl(record.value(),
                                this.commandPublisher,
                                this.kafkaMessageProducer,
                                this.commandAuditRepo));
            }
        }
    }
}
