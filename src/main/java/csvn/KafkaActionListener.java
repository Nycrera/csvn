package csvn;

import core.kafka.communication.types.Action;

public interface KafkaActionListener{
	void KafkaAction(Action data);
}
