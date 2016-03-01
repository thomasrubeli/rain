package game_screen;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;


public abstract class SoundEffects {
	private File file;
	protected Clip clip;
	public SoundEffects(String s) {
		file=new File(s);
		try {
			
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(file);
            DataLine.Info info = new DataLine.Info(Clip.class, inputStream.getFormat());
            clip = (Clip) AudioSystem.getLine(info);
			clip.open(inputStream);
		} catch (Exception e) {
			System.err.println("Sound effect loading error: " + e.getMessage());
		}
		
	}
	public abstract void playSound();
	
	public void reloadSound(){
		try {
			clip = AudioSystem.getClip();
			AudioInputStream inputStream = AudioSystem.getAudioInputStream(file);
			DataLine.Info info = new DataLine.Info(Clip.class, inputStream.getFormat());
            clip = (Clip) AudioSystem.getLine(info);
			clip.open(inputStream);
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedAudioFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
