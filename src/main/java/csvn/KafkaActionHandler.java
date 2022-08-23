package csvn;

import csvn.KafkaActionListener;
import csvn.pubsub.ActionConsumer;

public class KafkaActionHandler  implements KafkaActionListener{
	public KafkaActionHandler() {
		ActionConsumer ac = new ActionConsumer();
		ac.registerActionListener(this);
		ac.startConsumer();
	}
	
	public void KafkaAction(String data) {
		System.out.println(data);
	}
}
