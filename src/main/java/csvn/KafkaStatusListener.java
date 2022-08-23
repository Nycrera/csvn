/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package csvn;

/**
 *
 * @author asimkaymak
 */


import core.kafka.communication.types.Status;

public interface KafkaStatusListener{
	void KafkaAction(Status data);
}
