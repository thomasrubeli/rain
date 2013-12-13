package menu_screen;

import java.io.File;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.SourceDataLine;

public class MusicThread extends Thread {
	private boolean stopMusic;
	private File f;
	private static SourceDataLine sourcedataline;

	public MusicThread(String path) {
		this.f = new File(path);
	}

	public void toggleMusic() {
		if (sourcedataline == null) {
			stopMusic = false;
			this.start();
			return;
		}
		if (stopMusic) {
			stopMusic = false;
		} else
			stopMusic = true;

	}

	public void run() {
		try {
			// get an AudioInputStream
			AudioInputStream ais = AudioSystem.getAudioInputStream(f);
			// get the AudioFormat for the AudioInputStream
			AudioFormat audioformat = ais.getFormat();
			// System.out.println("Format: " + audioformat.toString());
			// System.out.println("Encoding: " + audioformat.getEncoding());
			// System.out.println("SampleRate:" +
			// audioformat.getSampleRate());
			// System.out.println("SampleSizeInBits: " +
			// audioformat.getSampleSizeInBits());
			// System.out.println("Channels: " + audioformat.getChannels());
			// System.out.println("FrameSize: " +
			// audioformat.getFrameSize());
			// System.out.println("FrameRate: " +
			// audioformat.getFrameRate());
			// System.out.println("BigEndian: " +
			// audioformat.isBigEndian());
			// ULAW format to PCM format conversion
			if ((audioformat.getEncoding() == AudioFormat.Encoding.ULAW)
					|| (audioformat.getEncoding() == AudioFormat.Encoding.ALAW)) {
				AudioFormat newformat = new AudioFormat(
						AudioFormat.Encoding.PCM_SIGNED,
						audioformat.getSampleRate(),
						audioformat.getSampleSizeInBits() * 2,
						audioformat.getChannels(),
						audioformat.getFrameSize() * 2,
						audioformat.getFrameRate(), true);
				ais = AudioSystem.getAudioInputStream(newformat, ais);
				audioformat = newformat;
			}

			// checking for a supported output line
			DataLine.Info datalineinfo = new DataLine.Info(
					SourceDataLine.class, audioformat);
			if (!AudioSystem.isLineSupported(datalineinfo)) {
				// System.out.println("Line matching " + datalineinfo +
				// " is not supported.");
			} else {
				// System.out.println("Line matching " + datalineinfo +
				// " is supported.");
				// opening the sound output line
				sourcedataline = (SourceDataLine) AudioSystem
						.getLine(datalineinfo);
				sourcedataline.open(audioformat, 10000);
				sourcedataline.start();
				// Copy data from the input stream to the output data line
				int framesizeinbytes = audioformat.getFrameSize();
				int bufferlengthinframes = sourcedataline.getBufferSize() / 8;
				int bufferlengthinbytes = bufferlengthinframes
						* framesizeinbytes;
				byte[] sounddata = new byte[bufferlengthinbytes];
				int numberofbytesread = 0;
				while (true) {
					while ((numberofbytesread = ais.read(sounddata)) != -1) {
						while (stopMusic) {
							Thread.sleep(10);
						}
						int numberofbytesremaining = numberofbytesread;
						if (!stopMusic) {
							sourcedataline.write(sounddata, 0,
									numberofbytesread);
						}
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static FloatControl getVolumeControl() {
		if (sourcedataline!=null && sourcedataline.isControlSupported(FloatControl.Type.MASTER_GAIN)) {			
			FloatControl volume = (FloatControl) sourcedataline
					.getControl(FloatControl.Type.MASTER_GAIN);
			return volume;
		}

		return null;

	}
	public static FloatControl getPanControl() {
		if (sourcedataline!=null && sourcedataline.isControlSupported(FloatControl.Type.PAN)) {
			FloatControl pan = (FloatControl) sourcedataline
					.getControl(FloatControl.Type.PAN);
			return pan;
		}

		return null;

	}
}
