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
import core.kafka.communication.types.Action;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import core.kafka.serialization.ObjectDeserializer;
import csvn.KafkaActionListener;

public class ActionConsumer {
    static Logger logger = LogManager.getLogger(ActionProducer.class.getName());
    
    public static KafkaActionListener KafkaListener;
    public void registerActionListener(KafkaActionListener listener) {
    	KafkaListener = listener;
    }
    
    public void startConsumer() {
        KafkaConsumer<String, Action> kafkaConsumer = getKafkaConsumer();
        kafkaConsumer.subscribe(Collections.singletonList(IAppConfigs.ACTION_TOPIC));
        logger.info("Consumer initialized!");
        while (true) {
            try {
                ConsumerRecords<String, Action> actionRecords = kafkaConsumer.poll(Duration.ofSeconds(1));
                actionRecords.forEach(record -> {
                    //System.out.println(record.value().toString());
                    // Something happened
                    KafkaListener.KafkaAction(record.value());
                });
            } catch (NullPointerException npe) {
                npe.printStackTrace();
            }
        }
    }

    public static KafkaConsumer<String, Action> getKafkaConsumer() {
        KafkaConsumer<String, Action> kafkaConsumer = new KafkaConsumer<String, Action>(getKafkaConsumerConfig());
        return kafkaConsumer;
    }

    private static Properties getKafkaConsumerConfig() {
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
