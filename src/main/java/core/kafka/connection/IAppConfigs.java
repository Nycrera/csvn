/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.kafka.connection;

/**
 *
 * @author asimkaymak
 */
public interface IAppConfigs {
    String BOOTSTAP_SERVER="localhost:9092";
    String APPLICATION_ID_CONFIG="order-id-config";
    String STATUS_TOPIC="statusss";
    String ACTION_TOPIC="actionn";
    String SENDER_ID="client_1";
}
