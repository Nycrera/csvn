/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package csvn.pubsub;

/**
 *
 * @author asimkaymak
 */

import core.kafka.connection.IAppConfigs;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import java.time.Duration;
import java.util.Collections;
import java.util.Properties;
import core.kafka.communication.types.Status;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import core.kafka.serialization.ObjectDeserializer;
import csvn.KafkaStatusListener;


public class StatusConsumer {
    static Logger logger = LogManager.getLogger(StatusProducer.class.getName());
     public static KafkaStatusListener KafkaListener;
    public void registerStatusListener(KafkaStatusListener listener) {
    	KafkaListener = listener;
    }

    public void startConsumer() {
        KafkaConsumer<String, Status> kafkaConsumer = getKafkaConsumer();
        kafkaConsumer.subscribe(Collections.singletonList(IAppConfigs.STATUS_TOPIC));
        logger.info("Consumer initialized!");
        while (true) {
            try {
                ConsumerRecords<String, Status> orderRecords = kafkaConsumer.poll(Duration.ofSeconds(1));
                orderRecords.forEach(record -> {
                    //System.out.println(record.value().toString());
                    KafkaListener.KafkaAction(record.value());

                });
            } catch (NullPointerException npe) {
                npe.printStackTrace();
            }
        }
    }

    public KafkaConsumer<String, Status> getKafkaConsumer() {
        KafkaConsumer<String, Status> kafkaConsumer = new KafkaConsumer<String, Status>(getKafkaConsumerConfig());
        return kafkaConsumer;
    }

    private  Properties getKafkaConsumerConfig() {
        Properties consumerProps = new Properties();
        consumerProps.put(ConsumerConfig.CLIENT_ID_CONFIG, IAppConfigs.APPLICATION_ID_CONFIG);
        consumerProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, IAppConfigs.BOOTSTAP_SERVER);
        consumerProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        consumerProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ObjectDeserializer.class);
        // consumerProps.put(CustomDeserializer.VALUE_CLASS_NAME_CONFIG, OrderInvoice.class);
        consumerProps.put(ConsumerConfig.GROUP_ID_CONFIG, "Sample-grp_id");
        consumerProps.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
        return consumerProps;
    }
}
