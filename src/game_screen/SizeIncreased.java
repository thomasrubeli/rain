package game_screen;

public class SizeIncreased extends SoundEffects {
	
	public SizeIncreased(String s) {
		super(s);
	}

	@Override
	public void playSound() {
		super.clip.start();
		super.reloadSound();
	}

}
