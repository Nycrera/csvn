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
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;
import core.kafka.communication.types.Action;
import core.models.DateConverter;
import csvn.Util;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import core.kafka.serialization.ObjectSerializer;

public class ActionProducer {

    static Logger logger = LogManager.getLogger(StatusProducer.class.getName());
    static KafkaProducer<String, Action> kafkaProducer = new KafkaProducer<String, Action>(getKafkaProducerConfig());
    
    public static void Send(String ActionName, String Action, Map<String,String> Properties) throws Exception {
        Action action = new Action();
        action.setActionName(ActionName);
        action.setAction(Action);
        action.setActionProperties((new JSONObject(Properties)).toString());
        String senderid = Util.DetectIfServer() ? "Server" : Util.DetectOpconUsingIP();
        action.setSenderId(senderid);
        action.setActionDate(DateConverter.dateToLong());
        ProducerRecord<String, Action> statusRecord = new ProducerRecord<>(IAppConfigs.ACTION_TOPIC,
                "action", action);
        kafkaProducer.send(statusRecord);
        logger.info("Event published...");
    }
    
    public static void Close() throws InterruptedException {
        kafkaProducer.flush();
        kafkaProducer.close();
        Thread.sleep(2);
        logger.info("Producer closed...");
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
