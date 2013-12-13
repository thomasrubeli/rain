package game_screen;

public class DeathSound extends SoundEffects {
	
	public DeathSound(String s) {
		super(s);
	}

	@Override
	public void playSound() {
		super.clip.start();
		super.reloadSound();
	}

}
