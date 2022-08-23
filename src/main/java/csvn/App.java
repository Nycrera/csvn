package csvn;

import core.kafka.communication.types.Status;
import java.lang.reflect.InvocationTargetException;

public class App {
<<<<<<< Updated upstream
	public static void main(String[] args) {
		System.out.println("Hello world.");
		csvnUI ui = new csvnUI();
		ui.main(args);
=======
	public static void main(String[] args) throws InterruptedException, InvocationTargetException {
		csvnUI ui = new csvnUI();
                ui.main(args);
                
                
>>>>>>> Stashed changes
	}
        public static Status vericek(){
            Status s = new Status();
                s.setDiskSize(3002123);
                s.setUsableDiskPartition(2931231);
                return s;
        }
}
