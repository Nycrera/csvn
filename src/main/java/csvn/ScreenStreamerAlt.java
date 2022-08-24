package csvn;

import java.io.BufferedReader;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.bytedeco.ffmpeg.global.avcodec;
import org.bytedeco.ffmpeg.global.avutil;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.FFmpegFrameRecorder;
import org.bytedeco.javacv.FFmpegFrameRecorder.Exception;
import org.bytedeco.javacv.Frame;

public class ScreenStreamerAlt {
	FFmpegFrameGrabber videoGrabber;
	//FFmpegFrameGrabber audioGrabber;
	FFmpegFrameRecorder recorder;
	public int Width = 1280	;
	public int Height = 1024;
	public int DisplayId = 0;
	public int FPS = 30;
	boolean running = false;
	Thread t;
	ScheduledThreadPoolExecutor AudioThread;

	/**
	 * Initializes a ScreenStreamer with FFMpeg
	 *
	 * @param ipaddress   String containing the IP address of the receiving client.
	 * @param port        String containing the Port number of the receiving client.
	 * @param enableVAAPI Defines whether the video acceleration is used or not.
	 * @param inputStr    Defines the -i option for example use ":0.0".
	 * @throws Exception
	 * @throws java.lang.Exception
	 * @throws IllegalArgumentException
	 * @throws GstException
	 */
	ScreenStreamerAlt(String ipaddress, String port, boolean enableVAAPI, String inputStr) throws java.lang.Exception {
		
		videoGrabber = new FFmpegFrameGrabber(inputStr);
		videoGrabber.setFormat("x11grab");
		
		videoGrabber.setFrameRate(FPS);
		videoGrabber.setImageWidth(Width);
		videoGrabber.setImageHeight(Height);
		videoGrabber.start();

		//audioGrabber = new FFmpegFrameGrabber("hw:0");
		//audioGrabber.setFormat("alsa");
		//audioGrabber.start();

		recorder = new FFmpegFrameRecorder("rtp://"+ipaddress+":"+port, videoGrabber.getImageWidth(),
				videoGrabber.getImageHeight()); // audioGrabber.getAudioChannels());
		recorder.setFormat("rtp_mpegts");

		if (enableVAAPI) {
			recorder.setVideoOption("hwaccel", "vaapi");
			recorder.setVideoOption("hwaccel_device", "/dev/dri/renderD128");
			recorder.setVideoOption("hwaccel_output_format", "vaapi");
			recorder.setVideoOption("vf", "scale_vaapi=format=nv12");
			recorder.setVideoCodecName("h264_vaapi");
			recorder.setPixelFormat(avutil.AV_PIX_FMT_VAAPI);
		} else {
			recorder.setVideoCodec(avcodec.AV_CODEC_ID_H264);
		}
		// Key frame interval, in our case every 2 seconds -> 30 (fps) * 2 = 60
		// (gop length)
		recorder.setGopSize(60);
		// recorder.setVideoCodec(avcodec.AV_CODEC_ID_H265);

	    //recorder.setAudioCodec(avcodec.AV_CODEC_ID_AAC);
		//recorder.setSampleRate(audioGrabber.getSampleRate());
		recorder.setVideoBitrate(1 * 1000 * 1000);
		//recorder.setAudioBitrate(48 * 1000);
		recorder.setFrameRate(30);
		//recorder.setInterleaved(true);
	}

	/**
	 * Start the streaming.
	 * 
	 * @throws Exception
	 */
	public void Start() throws Exception {
		running = true;
		recorder.start();

		Runnable runnable = () -> { // Video Streaming Thread
			try {
				Frame videoFrame;
				while (running) { // Streaming loop
					videoFrame = videoGrabber.grabAtFrameRate();
					if (videoFrame != null) {
						recorder.record(videoFrame);
					}
				}
			} catch (java.lang.Exception e) {
				e.printStackTrace();
			}
		};

		/*AudioThread = new ScheduledThreadPoolExecutor(1);
		AudioThread.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				try {
					recorder.recordSamples(audioGrabber.getSampleRate(), audioGrabber.getAudioChannels(),
							audioGrabber.grabSamples().samples);
				} catch (java.lang.Exception e) {
					e.printStackTrace();
				}
			}
		}, 0, (long) 1000 / FPS, TimeUnit.MILLISECONDS);
*/
		t = new Thread(runnable);
		t.start();
	}

	/**
	 * Stop the streaming.
	 */
	public void Stop() {
		try {
			running = false;
			videoGrabber.stop();
			videoGrabber.close();
		//	audioGrabber.stop();
		//	audioGrabber.close();
			recorder.stop();
			recorder.close();
			if(t.isAlive()) {
				t.stop(); // Unsafe stop, last resort, shouldn't even need to execute anyway.
			}
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}
	}
}
