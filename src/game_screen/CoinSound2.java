package game_screen;

public class CoinSound2 extends SoundEffects {
	
	public CoinSound2(String s) {
		super(s);
	}

	@Override
	public void playSound() {
		super.clip.start();
		super.reloadSound();
	}

}
