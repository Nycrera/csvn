package csvn;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import core.kafka.communication.types.Action;
import core.kafka.communication.types.Record;
import csvn.pubsub.ActionConsumer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class KafkaActionHandler implements KafkaActionListener {

    csvnUI ui;
    Map<String, String> config;
    Map<String, ScreenStreamer> streamers = new HashMap<String, ScreenStreamer>();
    Map<String, ScreenStreamerAlt> streamersAlt = new HashMap<String, ScreenStreamerAlt>();
    Map<String, StreamPlayer> players = new HashMap<String, StreamPlayer>();
    Map<String, StreamRecorder> recorders = new HashMap<String, StreamRecorder>();
    Map<String, VideoStreamer> vstreamers = new HashMap<String, VideoStreamer>();
    Map<String, VideoStreamerAlt> vstreamersAlt = new HashMap<String, VideoStreamerAlt>();

    public KafkaActionHandler(csvnUI UI) {
        ui = UI;
        config = Util.getVideoConfig();
        ActionConsumer ac = new ActionConsumer();
        ac.registerActionListener(this);
        ac.startConsumer();
    }

    public void KafkaAction(Action data) {
        try {
            String opconID = Util.DetectOpconUsingIP(); // Better done for once in main and passed down.
            JSONObject jo = new JSONObject(data.getActionProperties());
            Map<String, Object> propertyMap = Util.toMap(jo);

            switch (data.getActionName()) {
                case "STREAM":
                    if (propertyMap.get("TO").equals(opconID)) {
                        if (data.getAction().equals("START")) {
                            if (players.get((String) propertyMap.get("MULTICASTIP") + ":"
                                    + (String) propertyMap.get("MULTICASTPORT")) != null) {
                                return; // Ignore if player already is running
                            }
                            StreamPlayer player = new StreamPlayer((String) propertyMap.get("MULTICASTIP"),
                                    (String) propertyMap.get("MULTICASTPORT"));
                            players.put((String) propertyMap.get("MULTICASTIP") + ":"
                                    + (String) propertyMap.get("MULTICASTPORT"), player);
                            player.Start();
                        } else if (data.getAction().equals("STOP")) {
                            StreamPlayer player = players.get((String) propertyMap.get("MULTICASTIP") + ":"
                                    + (String) propertyMap.get("MULTICASTPORT"));
                            if (player != null) {
                                Runnable runnable = () -> { // FFMpeg Thread
                                    try {
                                player.Stop();
                                players.remove((String) propertyMap.get("MULTICASTIP") + ":"
                                        + (String) propertyMap.get("MULTICASTPORT"));
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                };

                                Thread t = new Thread(runnable);
                                t.start();
                            }
                        }
                    }
                    if (propertyMap.get("FROM").equals(opconID)) {
                        if (data.getAction().equals("START")) {

                            if(config.get("ssmethod").equals("gstreamer")) {
                                if (streamers.get((String) propertyMap.get("MULTICASTIP") + ":"
                                        + (String) propertyMap.get("MULTICASTPORT")) != null) {
                                    return; // Ignore if stream already is running
                                }
                            ScreenStreamer streamer = new ScreenStreamer((String) propertyMap.get("MULTICASTIP"),
                                  (String) propertyMap.get("MULTICASTPORT"), Boolean.parseBoolean(config.get("ssvaapi")));
                            streamers.put((String) propertyMap.get("MULTICASTIP") + ":"
                             + (String) propertyMap.get("MULTICASTPORT"), streamer);
                                  streamer.Start();
                            }else if(config.get("ssmethod").equals("ffmpeg")) {
                                if (streamersAlt.get((String) propertyMap.get("MULTICASTIP") + ":"
                                        + (String) propertyMap.get("MULTICASTPORT")) != null) {
                                    return; // Ignore if stream already is running
                                }
                                ScreenStreamerAlt streamer = new ScreenStreamerAlt((String) propertyMap.get("MULTICASTIP"),
                                        (String) propertyMap.get("MULTICASTPORT"), Boolean.parseBoolean(config.get("ssvaapi")), config.get("ssinptstr"));
                                  streamersAlt.put((String) propertyMap.get("MULTICASTIP") + ":"
                                   + (String) propertyMap.get("MULTICASTPORT"), streamer);
                                  streamer.Start();
                            }
                            
                        } else if (data.getAction().equals("STOP")) {
                            if(config.get("ssmethod").equals("gstreamer")) {
                        	ScreenStreamer streamer = streamers.get((String) propertyMap.get("MULTICASTIP") + ":"
                                    + (String) propertyMap.get("MULTICASTPORT"));
                            if (streamer != null) {
                                streamer.Stop();
                                streamers.remove((String) propertyMap.get("MULTICASTIP") + ":"
                                        + (String) propertyMap.get("MULTICASTPORT"));
                            }
                            }else if(config.get("ssmethod").equals("ffmpeg")) {
                            	ScreenStreamerAlt streamer = streamersAlt.get((String) propertyMap.get("MULTICASTIP") + ":"
                                        + (String) propertyMap.get("MULTICASTPORT"));
                                if (streamer != null) {
                                    streamer.Stop();
                                    streamersAlt.remove((String) propertyMap.get("MULTICASTIP") + ":"
                                            + (String) propertyMap.get("MULTICASTPORT"));
                                }
                            }
                        }
                    }
                    break;
                case "RECORD":
                    if (Util.DetectIfServer()) {
                        if (data.getAction().equals("START")) {
                            if (recorders.get((String) propertyMap.get("MULTICASTIP") + ":"
                                    + (String) propertyMap.get("MULTICASTPORT")) != null) {
                                return; // Ignore if recording already is running
                            }
                            Runnable runnable = () -> { // FFMpeg Thread
                                try {

                                    StreamRecorder recorder = new StreamRecorder((String) propertyMap.get("MULTICASTIP"),
                                            (String) propertyMap.get("MULTICASTPORT"), "/var/tmp", (String) propertyMap.get("FROM"),
                                            (String) propertyMap.get("NAME"), (String) propertyMap.get("PRIORITY"),
                                            Long.parseLong((String) propertyMap.get("PERIOD")) * 60 * 1000 * 1000);
                                    recorders.put((String) propertyMap.get("MULTICASTIP") + ":"
                                            + (String) propertyMap.get("MULTICASTPORT"), recorder);
                                    recorder.Start();

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            };

                            Thread t = new Thread(runnable);
                            t.start();

                            Record rc = ServerManager.consoleRecordStatus.get(Integer.valueOf(String.valueOf(propertyMap.get("FROM")).replaceAll("OPCON-", "")) - 1);
                            rc.setStartTime(data.getActionDate());
                            rc.setName(String.valueOf(propertyMap.get("NAME")));
                            rc.setStatus(false);
                            
                            Util.UpdateStatusXml(rc.getSource(), rc.getName() , Long.valueOf(String.valueOf(rc.getStartTime())));
                            
                            ServerManager.consoleRecordStatus.set(Integer.valueOf(String.valueOf(propertyMap.get("FROM")).replaceAll("OPCON-", "")) - 1, rc);
                           
                        } else if (data.getAction().equals("STOP")) {
                            Runnable runnable = () -> {
                                StreamRecorder recorder = recorders.get((String) propertyMap.get("MULTICASTIP") + ":"
                                        + (String) propertyMap.get("MULTICASTPORT"));
                                if (recorder != null) {
                                    try {
                                        recorder.Stop();
                                    } catch (Exception ex) {
                                        Logger.getLogger(KafkaActionHandler.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                    recorders.remove((String) propertyMap.get("MULTICASTIP") + ":"
                                            + (String) propertyMap.get("MULTICASTPORT"));
                                }
                            };
                            Thread t = new Thread(runnable);
                            t.start();

                            Record rc = ServerManager.consoleRecordStatus.get(Integer.valueOf(String.valueOf(propertyMap.get("FROM")).replaceAll("OPCON-", "")) - 1);
                            
                            rc.setStartTime(null);
                            rc.setName("");
                            rc.setStatus(true);
                            
                            Util.ClearStatusXml(rc.getSource());
                            
                            ServerManager.consoleRecordStatus.set(Integer.valueOf(String.valueOf(propertyMap.get("FROM")).replaceAll("OPCON-", "")) - 1, rc);

                        }
                    }
                    if (propertyMap.get("FROM").equals(opconID)) { // Need to take action, I am the referenced client
                        if (data.getAction().equals("START")) {
                            Runnable runnable = () -> { // FFMpeg Thread
                                try {
                                    if(config.get("ssmethod").equals("gstreamer")) {
                                        if (streamers.get((String) propertyMap.get("MULTICASTIP") + ":"
                                                + (String) propertyMap.get("MULTICASTPORT")) != null) {
                                            return; // Ignore if stream already is running
                                        }
                                    ScreenStreamer streamer = new ScreenStreamer((String) propertyMap.get("MULTICASTIP"),
                                          (String) propertyMap.get("MULTICASTPORT"), Boolean.parseBoolean(config.get("ssvaapi")));
                                    streamers.put((String) propertyMap.get("MULTICASTIP") + ":"
                                     + (String) propertyMap.get("MULTICASTPORT"), streamer);
                                          streamer.Start();
                                    }else if(config.get("ssmethod").equals("ffmpeg")) {
                                        if (streamersAlt.get((String) propertyMap.get("MULTICASTIP") + ":"
                                                + (String) propertyMap.get("MULTICASTPORT")) != null) {
                                            return; // Ignore if stream already is running
                                        }
                                        ScreenStreamerAlt streamer = new ScreenStreamerAlt((String) propertyMap.get("MULTICASTIP"),
                                                (String) propertyMap.get("MULTICASTPORT"), Boolean.parseBoolean(config.get("ssvaapi")), config.get("ssinptstr"));
                                          streamersAlt.put((String) propertyMap.get("MULTICASTIP") + ":"
                                           + (String) propertyMap.get("MULTICASTPORT"), streamer);
                                          streamer.Start();
                                    }
                                } catch (Exception err) {
                                    err.printStackTrace();
                                }
                            };
                            Thread t = new Thread(runnable);
                            t.start();
                        } else if (data.getAction().equals("STOP")) {
                            Runnable runnable = () -> {
                                if(config.get("ssmethod").equals("gstreamer")) {
                                	ScreenStreamer streamer = streamers.get((String) propertyMap.get("MULTICASTIP") + ":"
                                            + (String) propertyMap.get("MULTICASTPORT"));
                                    if (streamer != null) {
                                        streamer.Stop();
                                        streamers.remove((String) propertyMap.get("MULTICASTIP") + ":"
                                                + (String) propertyMap.get("MULTICASTPORT"));
                                    }
                                    }else if(config.get("ssmethod").equals("ffmpeg")) {
                                    	ScreenStreamerAlt streamer = streamersAlt.get((String) propertyMap.get("MULTICASTIP") + ":"
                                                + (String) propertyMap.get("MULTICASTPORT"));
                                        if (streamer != null) {
                                            streamer.Stop();
                                            streamersAlt.remove((String) propertyMap.get("MULTICASTIP") + ":"
                                                    + (String) propertyMap.get("MULTICASTPORT"));
                                        }
                                    }
                            };
                            Thread t = new Thread(runnable);
                            t.start();

                        }
                    }
                    break;

                case "REPLAY":
                    if (Util.DetectIfServer()) {
                        if (data.getAction().equals("START")) {
                            if(config.get("vsmethod").equals("gstreamer")) {
                            if (vstreamers.get((String) propertyMap.get("MULTICASTIP") + ":"
                                    + (String) propertyMap.get("MULTICASTPORT")) != null) {
                                return; // Ignore if replay already is running
                            }
                            VideoStreamer vstreamer = new VideoStreamer("/var/tmp/" + (String) propertyMap.get("FILE"),
                                    (String) propertyMap.get("MULTICASTIP"), (String) propertyMap.get("MULTICASTPORT"));
                            vstreamers.put((String) propertyMap.get("MULTICASTIP") + ":"
                                    + (String) propertyMap.get("MULTICASTPORT"), vstreamer);
                            vstreamer.Start();
                            }else if(config.get("vsmethod").equals("ffmpeg")) {
                                if (vstreamersAlt.get((String) propertyMap.get("MULTICASTIP") + ":"
                                        + (String) propertyMap.get("MULTICASTPORT")) != null) {
                                    return; // Ignore if replay already is running
                                }
                                VideoStreamerAlt vstreamer = new VideoStreamerAlt("/var/tmp/" + (String) propertyMap.get("FILE"),
                                        (String) propertyMap.get("MULTICASTIP"), (String) propertyMap.get("MULTICASTPORT"));
                                vstreamersAlt.put((String) propertyMap.get("MULTICASTIP") + ":"
                                        + (String) propertyMap.get("MULTICASTPORT"), vstreamer);
                                vstreamer.Start();
                            }
                        } else if (data.getAction().equals("STOP")) {
                            if(config.get("vsmethod").equals("gstreamer")) {
                            VideoStreamer vstreamer = vstreamers.get((String) propertyMap.get("MULTICASTIP") + ":"
                                    + (String) propertyMap.get("MULTICASTPORT"));
                            if (vstreamer != null) {
                                vstreamer.Stop();
                                vstreamers.remove((String) propertyMap.get("MULTICASTIP") + ":"
                                        + (String) propertyMap.get("MULTICASTPORT"));
                            }
                            }else if(config.get("vsmethod").equals("ffmpeg")) {
                                VideoStreamerAlt vstreamer = vstreamersAlt.get((String) propertyMap.get("MULTICASTIP") + ":"
                                        + (String) propertyMap.get("MULTICASTPORT"));
                                if (vstreamer != null) {
                                    vstreamer.Stop();
                                    vstreamersAlt.remove((String) propertyMap.get("MULTICASTIP") + ":"
                                            + (String) propertyMap.get("MULTICASTPORT"));
                                }
                            }
                        } else if (data.getAction().equals("SEEK")) { // This is non finished code beware! only use with gstreamer.
                            VideoStreamer vstreamer = vstreamers.get((String) propertyMap.get("MULTICASTIP") + ":"
                                    + (String) propertyMap.get("MULTICASTPORT"));
                            if (vstreamer != null) {
                                vstreamer.Seek(Long.parseLong((String) propertyMap.get("TIME")));
                            }
                        }
                    }

                    if (propertyMap.get("TO").equals(opconID)) { // Need to take action, I am the referenced client
                        if (data.getAction().equals("START")) {
                            if (players.get((String) propertyMap.get("MULTICASTIP") + ":"
                                    + (String) propertyMap.get("MULTICASTPORT")) != null) {
                                return; // Ignore if player already is running
                            }
                            StreamPlayer player = new StreamPlayer((String) propertyMap.get("MULTICASTIP"),
                                    (String) propertyMap.get("MULTICASTPORT"));
                            players.put((String) propertyMap.get("MULTICASTIP") + ":"
                                    + (String) propertyMap.get("MULTICASTPORT"), player);
                            player.Start();
                        } else if (data.getAction().equals("STOP")) {
                            StreamPlayer player = players.get((String) propertyMap.get("MULTICASTIP") + ":"
                                    + (String) propertyMap.get("MULTICASTPORT"));
                            if (player != null) {
                                player.Stop();
                                players.remove((String) propertyMap.get("MULTICASTIP") + ":"
                                        + (String) propertyMap.get("MULTICASTPORT"));
                            }
                        }
                    }
                    break;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
