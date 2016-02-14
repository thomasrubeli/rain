package game_screen;

public class LifeLost extends SoundEffects {
	
	public LifeLost(String s) {
		super(s);
	}

	@Override
	public void playSound() {
		super.clip.start();
		super.reloadSound();
	}

}