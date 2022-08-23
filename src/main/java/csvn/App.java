package csvn;

import csvn.pubsub.ActionConsumer;

public class App {
	public static void main(String[] args) {
		System.out.println("Hello world.");
		Runnable runnable = () -> {
		KafkaActionHandler actionhandler = new KafkaActionHandler(); 
		};
		Thread KafkaActionReceiverThread = new Thread(runnable);
		KafkaActionReceiverThread.start();
		
		// Start UI
		csvnUI ui = new csvnUI();
		ui.main(args);
		
	}
}
