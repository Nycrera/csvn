package csvn;

public class App implements KafkaActionListener {
	public static void main(String[] args) {
		System.out.println("Hello world.");
		try {
		System.out.println(Util.DetectOpconUsingIP());
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		// Start UI
		csvnUI ui = new csvnUI();
		ui.main(args);
		
	}
	
	public void KafkaAction(String data) {
		
	}
}
