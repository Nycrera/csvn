package csvn;

import java.lang.reflect.InvocationTargetException;

import org.bytedeco.ffmpeg.global.avutil;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.FFmpegLogCallback;

import csvn.pubsub.ActionConsumer;

public class App {
	public static void main(String[] args) throws InvocationTargetException, InterruptedException {
		FFmpegLogCallback.setLevel(avutil.AV_LOG_INFO);
        FFmpegLogCallback.set(); // Sets FFMpeg to direct its logs.

        try {
            FFmpegFrameGrabber.tryLoad();
        } catch (org.bytedeco.javacv.FFmpegFrameGrabber.Exception e1) {
            e1.printStackTrace();
        }
        
		System.out.println("Hello world.");
		
		csvnUI ui = new csvnUI();
		Runnable runnable = () -> {
		KafkaActionHandler actionhandler = new KafkaActionHandler(ui); 
		};
		Thread KafkaActionReceiverThread = new Thread(runnable);
		KafkaActionReceiverThread.start();
		ui.main(args);
		
	}
}
