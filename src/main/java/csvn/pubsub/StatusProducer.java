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
import java.io.File;
import java.util.Properties;
import core.kafka.communication.types.Status;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import core.kafka.serialization.ObjectSerializer;

public class StatusProducer {

    static Logger logger = LogManager.getLogger(StatusProducer.class.getName());

    /*public static void main(String[] args) throws InterruptedException {
        KafkaProducer<String, Status> kafkaProducer = new KafkaProducer<String, Status>(getKafkaProducerConfig());
        Status status = getStatus();

        ProducerRecord<String, Status> statusRecord = new ProducerRecord<>(IAppConfigs.STATUS_TOPIC,
                "status", status);
        kafkaProducer.send(statusRecord);

        logger.info("Event published...");
        kafkaProducer.flush();
        kafkaProducer.close();
        logger.info("Producer closed...");
        Thread.sleep(2);

    }*/
    public static void publishMessage() throws InterruptedException{
        KafkaProducer<String, Status> kafkaProducer = new KafkaProducer<String, Status>(getKafkaProducerConfig());
        Status status = getStatus();

        ProducerRecord<String, Status> statusRecord = new ProducerRecord<>(IAppConfigs.STATUS_TOPIC,
                "status", status);
        kafkaProducer.send(statusRecord);

        logger.info("Event published...");
        kafkaProducer.flush();
        kafkaProducer.close();
        logger.info("Producer closed...");
        Thread.sleep(2);
    }

    private static Status getStatus() {
        Status status = new Status();
        File diskPartition = new File("/");
        long totalCapacity = diskPartition.getTotalSpace();
        long freePartitionSpace = diskPartition.getFreeSpace();
        long usablePatitionSpace = diskPartition.getUsableSpace();
        Boolean[] systemLiveStatus = new Boolean[]{true, true, true};
        Boolean[] consoleRecordStatus = new Boolean[]{true, false, true}; // konsol durum
        Boolean[] displayRecordStatus = new Boolean[]{true, true, true};
        status.setDiskSize(totalCapacity);
        status.setFreeDiskPartition(freePartitionSpace);
        status.setUsableDiskPartition(usablePatitionSpace);
        status.setSystemLiveStatus(systemLiveStatus);
        status.setConsoleRecordStatus(consoleRecordStatus);
        status.setDisplayRecordStatus(displayRecordStatus);

        return status;
    }

    private static Properties getKafkaProducerConfig() {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, IAppConfigs.BOOTSTAP_SERVER);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, ObjectSerializer.class);
        props.put(ProducerConfig.CLIENT_ID_CONFIG, IAppConfigs.APPLICATION_ID_CONFIG);
        return props;
    }

}
