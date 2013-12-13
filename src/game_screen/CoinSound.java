package game_screen;

public class CoinSound extends SoundEffects {
	
	public CoinSound(String s) {
		super(s);
	}

	@Override
	public void playSound() {
		super.clip.start();
		super.reloadSound();
	}

}
