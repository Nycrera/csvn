/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package csvn;

import core.connection.PingUtil;
import core.kafka.communication.types.Record;
import core.kafka.communication.types.Status;
import csvn.pubsub.StatusProducer;
import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author asimkaymak
 */
public class ServerManager {
    public static ArrayList<Boolean> consoleLiveStatus = Util.liveStatusCreator();
    public static ArrayList<Record> consoleRecordStatus = Util.recordStatusCreator();
    public static void main(String[] args) {
    	
        ScheduledThreadPoolExecutor publisherThread = new ScheduledThreadPoolExecutor(1);
        publisherThread.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                try {
                    StatusProducer statusProducer = new StatusProducer();
                    statusProducer.publishMessage(getStatus());
                } catch (InterruptedException e) {
                }
            }
        }, 0, 1500, TimeUnit.MILLISECONDS);
    }


    private static Status getStatus() {
        Status status = new Status();
        File diskPartition = new File("/");
        long totalCapacity = diskPartition.getTotalSpace();
        long freePartitionSpace = diskPartition.getFreeSpace();
        long usablePatitionSpace = diskPartition.getUsableSpace();

        //ping management
        ArrayList<Boolean> systemLiveStatus = PingUtil.opconPingController();
         // konsol durum
        Boolean[] displayRecordStatus = new Boolean[]{true, true, true};
        status.setDiskSize(totalCapacity);
        status.setFreeDiskPartition(freePartitionSpace);
        status.setUsableDiskPartition(usablePatitionSpace);
        status.setReplayFiles(Util.deneme());
        status.setOpconPingStatus(systemLiveStatus);
        status.setOpconLiveStatus(consoleLiveStatus);
        status.setOpconRecordStatus(consoleRecordStatus);
        status.setCoderPingStatus(PingUtil.coderPingController());
        

        return status;
    }
}
