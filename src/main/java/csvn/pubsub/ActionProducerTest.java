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
import java.util.Scanner;
import core.kafka.communication.types.Action;
import core.models.DateConverter;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import core.kafka.serialization.ObjectSerializer;

public class ActionProducerTest {

    static Logger logger = LogManager.getLogger(StatusProducer.class.getName());

    public static void main(String[] args) throws InterruptedException {
        KafkaProducer<String, Action> kafkaProducer = new KafkaProducer<String, Action>(getKafkaProducerConfig());
        Action action = getAction();

        ProducerRecord<String, Action> statusRecord = new ProducerRecord<>(IAppConfigs.ACTION_TOPIC,
                "action", action);
        kafkaProducer.send(statusRecord);

        logger.info("Event published...");
        kafkaProducer.flush();
        kafkaProducer.close();
        logger.info("Producer closed...");
        Thread.sleep(2);

    }

    private static Action getAction() {
        Action action = new Action();
        Scanner scanner = new Scanner(System.in);  // Create a Scanner object
        

        int record_type = 0;
        int command_type = 0;
        while (record_type != 1 && record_type != 2) {
            System.out.println("Lutfen kayit tipi seciniz :\n1- Konsol Kayıt\n2- Monitör Kayıt\n");
            record_type = scanner.nextInt();
        }

        while (command_type != 1 && command_type != 2 && command_type != 3) {
            System.out.println("Lutfen islemi seciniz :\n1- Kayit Baslat\n2- Kayit Durdur\n3- Kayit Kaydet\n");
            command_type = scanner.nextInt();
        }

        if (record_type == 1) {

            action.setActionName("console");
        } else if (record_type == 2) {

            action.setActionName("display");
        }

        if (command_type == 1) {
            action.setAction("start");

        } else if (command_type == 2) {

            action.setAction("stop");
        } else if (command_type == 3) {
            action.setAction("save");

        }
        System.out.println("Kayit özelliklerinizi giriniz : \n");
        String action_properties;
        scanner.nextLine();
        action_properties = scanner.nextLine();
        action.setSenderId(IAppConfigs.SENDER_ID);
        action.setActionDate(DateConverter.dateToLong());
        action.setActionProperties(action_properties);

        return action;
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
