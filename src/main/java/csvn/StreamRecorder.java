package csvn;

import java.io.File;
import java.nio.ByteBuffer;
import java.nio.ShortBuffer;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.bytedeco.ffmpeg.global.avcodec;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.FFmpegFrameRecorder;
import org.bytedeco.javacv.Frame;
import org.threeten.bp.Duration;

/**
 * <p>
 * Uses FFMpeg to record a video stream over the RTP Protocol. It is important
 * to set Width and Height properties, matching with the incoming stream.
 * Defaults to 1920x1080.
 * 
 * It is a must for the StreamRecorder.Stop() to be called at the end of the
 * stream. Otherwise resulting video file may be corrupted.
 * </p>
 */
public class StreamRecorder {

	public int Width = 1280;
	public int Height = 1024;
	private boolean Running = false;
	private FFmpegFrameGrabber Grabber;
	private FFmpegFrameRecorder Recorder;
	private String VideoFolder;
	private String VideoName;
	private String SourceName;
	private String Priority;
	private long Period;
	private long StartTimestamp = 0;
	private int VideoPartCounter = 1;

	/**
	 * Initializes a StreamRecorder.
	 *
	 * @param ipaddress  String containing the IP address of the receiving client.
	 * @param port       String containing the Port number of the receiving client.
	 * @param folder     Full or relative path of the folder that will contain the
	 *                   resulting video(s).
	 * @param sourcename Name of the source display or the camera.
	 * @param videoname  A custom name given by the user to this recording.
	 * @param priority   Priority of the video that will get added to metadata of
	 *                   the video(s).
	 * @param periodUs   Period to separate files. In microseconds.
	 * @throws IllegalArgumentException
	 * @throws Exception
	 */
	StreamRecorder(String ipaddress, String port, String folder, String sourcename, String videoname, String priority,
			long periodUs) throws IllegalArgumentException, Exception {
		if (!Util.ValidateData(ipaddress, port)) {
			throw new IllegalArgumentException();
		}

		this.VideoFolder = folder;
		this.VideoName = videoname;
		this.SourceName = sourcename;
		this.Priority = priority;
		this.Period = periodUs;
		Grabber = new FFmpegFrameGrabber("rtp://" + ipaddress + ":" + port);
		Grabber.setOption("protocol_whitelist", "rtp,udp,file,crypto");

	}

	/**
	 * Start the recording. It's usually better if this is ready before the stream
	 * begins. But not mandatory.
	 * 
	 * @throws Exception
	 */
	public void Start() throws Exception {
		Running = true;
		Grabber.setFrameRate(30);
		Grabber.setImageWidth(Width);
		Grabber.setImageHeight(Height);

		RunFFMpegThread();
	}

	/**
	 * Stops the player and saves the last video on disk. It is a must for this to
	 * be called at the end of the stream. Otherwise video output may get corrupted.
	 * 
	 * @throws Exception
	 */
	public void Stop() throws Exception {
		Running = false;
		Grabber.stop();
		Grabber.close();
		Recorder.stop();
		//Recorder.close(); -> this is unsafe as it kills the recorder prematurely, without calculating moov atoms.
	}

	private void RunFFMpegThread() {
		Runnable runnable = () -> { // FFMpeg Thread
			try {
				Grabber.start();
				Recorder = CreateRecorder(GenerateName());
				Recorder.start();
				Frame frame = null;
				while (Running) {
					frame = Grabber.grab();
					StartTimestamp = StartTimestamp == 0 ? Grabber.getTimestamp() : StartTimestamp;
					if (Grabber.getTimestamp() > (StartTimestamp + VideoPartCounter * Period)) { // All time here are in
						// microseconds.
						VideoPartCounter++;
						Recorder.stop();
						Recorder.close();

						Recorder = CreateRecorder(GenerateName());
						Recorder.start();
					}
					if (frame.image != null) {
						Recorder.record(frame);
					}
					/*if (frame.samples != null) {
						Recorder.recordSamples(Grabber.getSampleRate(), Grabber.getAudioChannels(), frame.samples);
					}*/

				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		};

		Thread t = new Thread(runnable);
		t.start();
	}

	private String GenerateName() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
		LocalDateTime now = LocalDateTime.now();
		return Paths.get(VideoFolder, String.format("%s_%s_%s.mp4", VideoName, SourceName, dtf.format(now))).toString();
	}

	private FFmpegFrameRecorder CreateRecorder(String filename) throws Exception {
		FFmpegFrameRecorder recorder = new FFmpegFrameRecorder(filename, Grabber.getImageWidth(),
				Grabber.getImageHeight());//, Grabber.getAudioChannels());
		recorder.setFormat("mp4");
		recorder.setFrameRate(30);
		recorder.setVideoCodec(avcodec.AV_CODEC_ID_H264);
		//recorder.setAudioCodec(avcodec.AV_CODEC_ID_AAC);
		//recorder.setAudioChannels(Grabber.getAudioChannels());
		//recorder.setSampleRate(Grabber.getSampleRate());

		recorder.setMetadata("comment", "priority=" + Priority);

		return recorder;
	}
}
