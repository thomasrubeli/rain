package menu_screen;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class ClipMusic {

	private static Clip clip;

	public ClipMusic(String filepath) {
		System.out.println(filepath);
		File file = new File(filepath);
		try {
			clip = AudioSystem.getClip();
			AudioInputStream inputStream = AudioSystem
					.getAudioInputStream(file);
			clip.open(inputStream);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

	public void play() {
		System.out.println("play");
		if (clip.isActive()) {
			clip.stop();
		}
		clip.setMicrosecondPosition(clip.getMicrosecondPosition());
		clip.start();
	}

	public void stop() {
		clip.stop();
	}

	public static FloatControl getVolumeControl() {
		if (clip != null) {
			return (FloatControl) clip
					.getControl(FloatControl.Type.MASTER_GAIN);
		}
		return null;

	}

	public static FloatControl getPanControl() {
		if (clip != null) {
			return (FloatControl) clip.getControl(FloatControl.Type.PAN);
		}
		return null;
	}
}
