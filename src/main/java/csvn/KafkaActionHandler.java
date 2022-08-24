package csvn;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import core.kafka.communication.types.Action;
import csvn.pubsub.ActionConsumer;

public class KafkaActionHandler implements KafkaActionListener {
	csvnUI ui;
	Map<String, ScreenStreamerAlt> streamers = new HashMap<String, ScreenStreamerAlt>();
	Map<String, StreamPlayer> players = new HashMap<String, StreamPlayer>();
	Map<String, StreamRecorder> recorders = new HashMap<String, StreamRecorder>();
	Map<String, VideoStreamer> vstreamers = new HashMap<String, VideoStreamer>();

	public KafkaActionHandler(csvnUI UI) {
		ui = UI;
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
				if (propertyMap.get("FROM").equals(opconID)) {
					if (data.getAction().equals("START")) {
						if (streamers.get((String) propertyMap.get("MULTICASTIP") + ":"
								+ (String) propertyMap.get("MULTICASTPORT")) != null)
							return; // Ignore if stream already is running
						ScreenStreamerAlt streamer = new ScreenStreamerAlt((String) propertyMap.get("MULTICASTIP"),
								(String) propertyMap.get("MULTICASTPORT"), false, ":0.0");
						streamers.put((String) propertyMap.get("MULTICASTIP") + ":"
								+ (String) propertyMap.get("MULTICASTPORT"), streamer);
						streamer.Start();
					} else if (data.getAction().equals("STOP")) {
						ScreenStreamerAlt streamer = streamers.get((String) propertyMap.get("MULTICASTIP") + ":"
								+ (String) propertyMap.get("MULTICASTPORT"));
						if (streamer != null) {
							streamer.Stop();
							streamers.remove((String) propertyMap.get("MULTICASTIP") + ":"
								+ (String) propertyMap.get("MULTICASTPORT"));
						}
					}
				}
				if (propertyMap.get("TO").equals(opconID)) {
					if (data.getAction().equals("START")) {
						if (players.get((String) propertyMap.get("MULTICASTIP") + ":"
								+ (String) propertyMap.get("MULTICASTPORT")) != null)
							return; // Ignore if player already is running
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
			case "RECORD":
				if (Util.DetectIfServer()) {
					if (data.getAction().equals("START")) {
						if (streamers.get((String) propertyMap.get("MULTICASTIP") + ":"
								+ (String) propertyMap.get("MULTICASTPORT")) != null)
							return; // Ignore if recording already is running
						StreamRecorder recorder = new StreamRecorder((String) propertyMap.get("MULTICASTIP"),
								(String) propertyMap.get("MULTICASTPORT"), "/var/tmp", (String) propertyMap.get("FROM"),
								(String) propertyMap.get("NAME"), (String) propertyMap.get("PRIORITY"),
								Long.parseLong((String) propertyMap.get("PERIOD")));
						recorders.put((String) propertyMap.get("MULTICASTIP") + ":"
								+ (String) propertyMap.get("MULTICASTPORT"), recorder);
						recorder.Start();
					} else if (data.getAction().equals("STOP")) {
						StreamRecorder recorder = recorders.get((String) propertyMap.get("MULTICASTIP") + ":"
								+ (String) propertyMap.get("MULTICASTPORT"));
						if (recorder != null) {
							recorder.Stop();
							recorders.remove((String) propertyMap.get("MULTICASTIP") + ":"
								+ (String) propertyMap.get("MULTICASTPORT"));
						}
					}
				}
				if (propertyMap.get("FROM").equals(opconID)) { // Need to take action, I am the referenced client
					if (data.getAction().equals("START")) {
						if (streamers.get((String) propertyMap.get("MULTICASTIP") + ":"
								+ (String) propertyMap.get("MULTICASTPORT")) != null)
							return; // Ignore if stream already is running
						ScreenStreamerAlt streamer = new ScreenStreamerAlt((String) propertyMap.get("MULTICASTIP"),
								(String) propertyMap.get("MULTICASTPORT"), false, ":0.0");
						streamers.put((String) propertyMap.get("MULTICASTIP") + ":"
								+ (String) propertyMap.get("MULTICASTPORT"), streamer);
						streamer.Start();
					} else if (data.getAction().equals("STOP")) {
						ScreenStreamerAlt streamer = streamers.get((String) propertyMap.get("MULTICASTIP") + ":"
								+ (String) propertyMap.get("MULTICASTPORT"));
						if (streamer != null) {
							streamer.Stop();
							streamers.remove((String) propertyMap.get("MULTICASTIP") + ":"
								+ (String) propertyMap.get("MULTICASTPORT"));
						}
					}
				}
				break;
			case "REPLAY":
				if (Util.DetectIfServer()) {
					if (data.getAction().equals("START")) {
						if (vstreamers.get((String) propertyMap.get("MULTICASTIP") + ":"
								+ (String) propertyMap.get("MULTICASTPORT")) != null)
							return; // Ignore if replay already is running
						VideoStreamer vstreamer = new VideoStreamer("/var/tmp/" + (String) propertyMap.get("FILE"),
								(String) propertyMap.get("MULTICASTIP"), (String) propertyMap.get("MULTICASTPORT"));
						vstreamers.put((String) propertyMap.get("MULTICASTIP") + ":"
								+ (String) propertyMap.get("MULTICASTPORT"), vstreamer);
						vstreamer.Start();
					} else if (data.getAction().equals("STOP")) {
						VideoStreamer vstreamer = vstreamers.get((String) propertyMap.get("MULTICASTIP") + ":"
								+ (String) propertyMap.get("MULTICASTPORT"));
						if (vstreamer != null) {
							vstreamer.Stop();
							vstreamers.remove((String) propertyMap.get("MULTICASTIP") + ":"
								+ (String) propertyMap.get("MULTICASTPORT"));
						}
					} else if(data.getAction().equals("SEEK")) {
						VideoStreamer vstreamer = vstreamers.get((String) propertyMap.get("MULTICASTIP") + ":"
								+ (String) propertyMap.get("MULTICASTPORT"));
						if (vstreamer != null)
							vstreamer.Seek(Long.parseLong((String) propertyMap.get("TIME")));
					}
				}

				if (propertyMap.get("TO").equals(opconID)) { // Need to take action, I am the referenced client
					if (data.getAction().equals("START")) {
						if (players.get((String) propertyMap.get("MULTICASTIP") + ":"
								+ (String) propertyMap.get("MULTICASTPORT")) != null)
							return; // Ignore if player already is running
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