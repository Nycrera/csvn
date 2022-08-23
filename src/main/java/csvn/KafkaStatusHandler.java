/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package csvn;

import java.util.HashMap;
import java.util.Map;

import core.kafka.communication.types.Action;
import core.kafka.communication.types.Status;
import csvn.KafkaStatusListener;
import csvn.pubsub.StatusConsumer;

public class KafkaStatusHandler  implements KafkaStatusListener{
	csvnUI ui;
	public KafkaStatusHandler(csvnUI UI) {
		ui = UI;
		StatusConsumer statusConsumer = new StatusConsumer();
		statusConsumer.registerStatusListener(this);
		statusConsumer.startConsumer();
	}
	
	public void KafkaAction(Status data) {
            App.status = data;
	}
}
