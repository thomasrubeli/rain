package game_screen;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.ConcurrentModificationException;
import java.util.Date;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ArrayBlockingQueue;

import javax.swing.JLabel;
import javax.swing.JPanel;

import menu_screen.BlocksGame;

public class GamePanel extends JPanel implements MouseListener,
		MouseMotionListener, KeyListener {
	private int mousePosX;
	private int mousePosY;
	private Timer timer;
	private TimerTask task;
	private ArrayBlockingQueue<DeathLine> lines = new ArrayBlockingQueue<DeathLine>(
			1000);
	private int level = 10;
	private BlocksGame mainFrame;
	private boolean starting;
	private int levelNum = 1;
	private JLabel label;
	public static int POINTS;
	public int BigLevel = 1;
	private boolean stopIterate;
	private boolean newLevel;
	public static int sphere_cons = 10;
	private int insideSphere = 0;
	private int lifes;
	private CoinSound coins = new CoinSound("sound_effects/coin_sound.wav");
	private LifeLost lifeLost = new LifeLost("sound_effects/lost_life.wav");
	private CoinSound2 coins2 = new CoinSound2("sound_effects/coin_sound2.wav");
	// private LifeGained lifeGained=new
	// LifeGained("sound_effects/life_gained.wav");
	private SizeIncreased size = new SizeIncreased(
			"sound_effects/size_increased.wav");
	private DeathSound death = new DeathSound("sound_effects/death.wav");
	private boolean soundEffects;
	private BlocksGame main;
	private Cursor blankCursor;
	private boolean drawNewLevel;
	private Robot mouseSetter;

	public GamePanel(BlocksGame frame) {
		main = frame;
		setBackground(Color.black);
		setPreferredSize(new Dimension(800, 800));
		addMouseListener(this);
		addMouseMotionListener(this);
		addKeyListener(this);
		// Transparent 16 x 16 pixel cursor image.
		BufferedImage cursorImg = new BufferedImage(16, 16,
				BufferedImage.TYPE_INT_ARGB);
		// Create a new blank cursor.
		blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImg,
				new Point(0, 0), "blank cursor");
		// Set the blank cursor to the JFrame.
		setCursor(blankCursor);
		task = new MoveBlocks();
		timer = new Timer();
		mainFrame = frame;
		new LevelThread().start();
		startMoveBlocks();
		try {
			mouseSetter=new Robot();
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private class MoveBlocks extends TimerTask {
		public void run() {
			if (!stopIterate) {
				if (starting) {
					POINTS++;
				}
				setCursor(blankCursor);
				repaint();
				if (lines.size() < level) {
				
					if (level % 2 == 0) {
						int x1 = randomInt();
						int y1 = randomInt();
						lines.add(new DeathLine(x1, x1, y1 - 1000, y1 - 1000,
								Color.red));
					}
					if (level % 6 == 0 || level % 4 == 0) {
						int x2 = randomInt();
						int y2 = randomInt();
						lines.add(new DeathLine(x2, x2, y2 - 700, y2 - 700,
								Color.green));
					}
					if (BigLevel > 2) {
						int x2 = randomInt();
						int y2 = randomInt();
						lines.add(new DeathLine(x2, x2, y2 - 700, y2 - 700,
								Color.MAGENTA));
					}
					if (BigLevel >= 1) {
						int x2 = randomInt();
						int y2 = randomInt();
						lines.add(new DeathLine(x2, x2, y2 - 700, y2 - 700,
								Color.WHITE));

					}
				}
			} else {
				try {
					Thread.sleep(30);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		mousePosX = e.getX();
		mousePosY = e.getY();

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		mousePosX = e.getX();
		mousePosY = e.getY();

	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void paint(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.white);
		g.setFont(new Font("TimesRoman", Font.PLAIN, 24));
		g.drawOval(mousePosX - 6, mousePosY - 9, sphere_cons, sphere_cons);
		g.setColor(Color.red);
		for (int i = 0; i < insideSphere; i++) {
			g.drawOval(mousePosX, mousePosY, i * i, i * i);
			if (i > 4) {
				g.setColor(Color.CYAN);
				g.drawOval(mousePosX - 56, mousePosY - 56, i * i, i * i);
			}
		}
		try {
			Iterator<DeathLine> it = lines.iterator();
			while (it.hasNext()) {
				DeathLine line = it.next();
				if (newLevel) {

					line.setNewLevel();
				}
				line.drawFall(g);
				// g.translate(30, 0);
				g.setColor(Color.blue);
			}
			newLevel = false;
			for (int i = 0; i < lifes; i++) {
				g.setColor(Color.white);
				g.drawOval(700, 700, 10, 10);
				g.translate(20, 0);
			}
			for (int i = 0; i < lifes; i++) {
				g.translate(-20, 0);
			}
			g.setColor(Color.gray);
			if (insideSphere != 0) {
				g.drawString("SCORE : " + POINTS * insideSphere, 20, 20);
			} else {
				g.drawString("SCORE : " + POINTS, 20, 20);
			}
			if (drawNewLevel) {
				g.drawString("Level :" + BigLevel, 375, 350);
			}
			if (!starting) {
				g.drawString("Level :" + BigLevel, 375, 350);
				g.drawString("Press space to start", 320, 370);
			}
		} catch (ConcurrentModificationException e) {
			e.printStackTrace();

		}
	}

	public void startMoveBlocks() {
		Date executionDate = new Date();// now
		timer.scheduleAtFixedRate(task, executionDate, 10);
	}

	public int randomInt() {
		return 4 + (int) (Math.random() * 1000) + 1;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			starting = true;
		}
		else if(e.getKeyCode()==KeyEvent.VK_ESCAPE){
			stopIterate = true;
			starting=false;
			mainFrame.setDeadScreen(-3, POINTS
					* insideSphere);
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	public void toggleStarting() {
		starting = !starting;
	}

	public void start() {
		int x=main.getX();
		int y=main.getY();
		mouseSetter.mouseMove(x+400,y+400);
		soundEffects = main.getSoundEffects();
		lifes = 3;
		stopIterate = false;
		starting = false;
		new collisionThread().start();
		sphere_cons = 10;
		insideSphere = 0;
		BigLevel = 1;
		POINTS = 0;
		levelNum = 0;
		level = 10;
		lines.clear();

	}

	private class collisionThread extends Thread {
		public collisionThread() {
			// TODO Auto-generated constructor stub
		}

		public void run() {
			while (true) {
				if(stopIterate){
					return;
				}
				if (starting) {
					boolean touched = false;
					Iterator<DeathLine> it = lines.iterator();
					while (it.hasNext()) {
						DeathLine line = it.next();
						touched = line.checkCollision(mousePosX - 6
								+ (sphere_cons / 2), mousePosY - 9
								+ (sphere_cons / 2));
						if (touched) {
							if (line.getColor().equals(Color.green)) {
								POINTS++;
								POINTS += BigLevel * 3;
								System.out.println(POINTS);
								if (soundEffects) {
									coins.playSound();
								}
								it.remove();
							} else if (line.getColor().equals(Color.MAGENTA)) {
								System.out.println("!!");
								POINTS = 3 * POINTS;
								if (soundEffects) {
									coins2.playSound();
								}
								it.remove();
							} else if (line.getColor().equals(Color.WHITE)) {
								it.remove();
//								sphere_cons += 2;
								if (soundEffects) {
									size.playSound();
								}
//								insideSphere++;
							} else if ((line.getColor().equals(Color.blue) || line
									.getColor().equals(Color.red)) && lifes > 0) {
								lifes--;
								if (soundEffects) {
									lifeLost.playSound();
								}
								System.out.println("You lost one life!");
								try {
									Thread.sleep(1000);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							} else {
								System.out.println("Oh. You are dead.");
								stopIterate = true;
								try {
									if (soundEffects) {
										death.playSound();
									}
									Thread.sleep(1000);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								if (insideSphere != 0) {
									mainFrame.setDeadScreen(BigLevel, POINTS
											* insideSphere);
								} else {
									mainFrame.setDeadScreen(BigLevel, POINTS);
								}
								return;
							}
						}
					}
				}
				try {
					Thread.sleep(2);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	private class LevelThread extends Thread {
		public void run() {
			while (true) {
				if (starting) {
					try {
						System.out.println("level increased");
						Thread.sleep(5000);
						levelNum++;
						if (levelNum % 3 == 0) {
							BigLevel++;
							DeathLine.toggleNextLevel();
							Thread.sleep(2000);
							drawNewLevel = true;
							Thread.sleep(3000);
							drawNewLevel = false;
							lines.clear();
							level = level + 4;
							newLevel = true;
							Thread.sleep(400);
							Iterator<DeathLine> it = lines.iterator();
							while (it.hasNext()) {
								it.next().setSpeed((levelNum / 2));
							}
							DeathLine.toggleNextLevel();

						}
						level += 4;
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				try {
					Thread.sleep(2);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

}
