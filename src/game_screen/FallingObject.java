package game_screen;

import java.awt.Color;
import java.awt.Graphics;

public abstract class FallingObject {
	protected int previousX;
	protected int currentX;
	protected int previousY;
	protected int currentY;
	protected int initialPreviousX;
	protected int initialCurrentX;
	protected int initialPreviousY;
	protected int initialCurrentY;
	protected int speed;
	protected Color color;
	protected static boolean nextLevel;
	protected boolean newLevel;
	public FallingObject(int a, int b, int c, int d, Color color) {
		this.previousX = a;
		this.currentX = b;
		this.previousY = c;
		this.currentY = d;
		this.initialPreviousX = a;
		this.initialCurrentX = b;
		this.initialPreviousY = c;
		this.initialCurrentY = d;
		this.speed = 3;
		this.color = color;
	}
	public abstract void drawFall(Graphics g);	
	public abstract int randomInt();
	public abstract boolean checkCollision(int x, int y);
	
	public void setSpeed(int sp) {
		speed = speed + sp;
	}
	public Color getColor() {
		return color;
	}

	public static void toggleNextLevel() {
		nextLevel = !nextLevel;
	}

	public void setNewLevel() {
		newLevel = true;
	}
	public int getCurrentY() {
		return currentY;
	}
	public int getPreviousX() {
		return previousX;
	}
	public int getCurrentX() {
		return currentX;
	}

}
