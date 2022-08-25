package csvn;

import core.kafka.communication.types.Status;
import java.lang.reflect.InvocationTargetException;

import org.bytedeco.ffmpeg.global.avutil;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.FFmpegLogCallback;
import org.freedesktop.gstreamer.Gst;
import org.freedesktop.gstreamer.glib.GLib;

import csvn.pubsub.ActionConsumer;

public class App {
    public static Status status;

    
    public static void main(String[] args) throws InvocationTargetException, InterruptedException {
        //SERVER CHECK
    	if(Gst.isInitialized()) Gst.deinit();
        if(Util.DetectIfServer()){
            System.out.println("ok server");
            ServerManager.main(args);
        }
        GLib.setEnv("GST_DEBUG", "4", true);
        
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
        
        
        Runnable runnable1 = () -> {
            KafkaStatusHandler statusHandler = new KafkaStatusHandler(ui);
        };
        Thread KafkaStatusReceiverThread = new Thread(runnable1);
        KafkaStatusReceiverThread.start();
        ui.main(args);
        
    }

    public static Status vericek() {
        
        return status;
    }
}
