package menu_screen;

import game_screen.GamePanel;

import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;

import javax.swing.JFrame;

public class BlocksGame extends JFrame implements KeyListener {
	private MenuPanel menu;
	private GamePanel game;
	private OptionPanel option;
	private boolean hasStarted;
	private boolean startedMusic;
	private MusicThread music;
	private StatsPanel stats;
	private boolean SoundEffects = false;
	private boolean firstPlay = true;

	public BlocksGame() {
		stats = new StatsPanel(this);
		menu = new MenuPanel(this, stats);
		game = new GamePanel(this);
		option = new OptionPanel(this);
		setPreferredSize(new Dimension(800, 800));
		// Move the window
		setLocation(500, 50);
		add(menu);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		addKeyListener(this);
		music = new MusicThread("assets/music/gone.ogg");
		setResizable(false);
		pack();
		setVisible(true);

	}

	public static void main(String[] args) {
		new BlocksGame();
	}

	public void startGame() {
		remove(menu);
		setContentPane(game);
		if (firstPlay) {
			if (!option.getMusicPlaying() && !option.userChoice()) {
				toggleMusic();
				option.setMusicOn();
			}
		}
		firstPlay=false;
		this.setFocusable(false);
		game.setFocusable(true);
		game.requestFocus(true);
		game.start();
		validate();
	}

	public void setDeadScreen(int level, int points) {
		remove(game);
		hasStarted = false;
		game.toggleStarting();
		menu.setdeadScreen(level, points);
		setContentPane(menu);
		validate();
	}

	public void setOptionPane() {
		remove(menu);
		setContentPane(option);
		this.setFocusable(false);
		option.setFocusable(true);
		option.requestFocus(true);
		validate();
	}

	public void setStatsPanel() {
		remove(menu);
		setContentPane(stats);
		this.setFocusable(false);
		stats.setFocusable(true);
		stats.requestFocus(true);
		validate();
	}

	public void setMain() {
		remove(option);
		setContentPane(menu);
		validate();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// if (e.getKeyChar() == 's') {
		// if (hasStarted) {
		// System.out.println("Start!");
		// game.toggleStarting();
		// }
		// }

	}

	public void toggleMusic() {
		if (!startedMusic) {
			music.toggleMusic();
			startedMusic = true;
		} else {
			music.toggleMusic();
			startedMusic = false;
		}
	}

	public void turnEffectsOn() {
		SoundEffects = true;
	}

	public void turnEffectsOff() {
		SoundEffects = false;
	}

	public boolean getSoundEffects() {
		return SoundEffects;
	}

}
