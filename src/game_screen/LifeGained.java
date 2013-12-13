package game_screen;

public class LifeGained extends SoundEffects {
	
	public LifeGained(String s) {
		super(s);
	}

	@Override
	public void playSound() {
		super.clip.start();
		super.reloadSound();
	}

}
