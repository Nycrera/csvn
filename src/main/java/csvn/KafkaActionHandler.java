package csvn;

import java.util.HashMap;
import java.util.Map;

import core.kafka.communication.types.Action;
import csvn.KafkaActionListener;
import csvn.pubsub.ActionConsumer;

public class KafkaActionHandler  implements KafkaActionListener{
	csvnUI ui;
	public KafkaActionHandler(csvnUI UI) {
		ui = UI;
		ActionConsumer ac = new ActionConsumer();
		ac.registerActionListener(this);
		ac.startConsumer();
	}
	
	public void KafkaAction(Action data) {
		try {
		String opconID = Util.DetectOpconUsingIP(); // Better done for once in main and passed down.
		
		switch(data.getActionName()) {
		case "STREAM":
			// TODO somehow parse data.getActionProperties(); TODO
			Map<String,String> propertyMap = new HashMap<String,String>();
			if(propertyMap.get("FROM").equals(opconID)) {
				ScreenStreamerAlt streamer = new ScreenStreamerAlt("239.0.4.101","5000",false,":0.0");
			}
			if(propertyMap.get("TO").equals(opconID)) {
				//START PLAYING STREAM
			}
			break;
		case "RECORD":
			break;
		}
		
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
