package game_screen;

import java.awt.Color;
import java.awt.Graphics;

public class DeathRectangle extends FallingObject {

	public DeathRectangle(int a, int b, int c, int d, Color color) {
		super(a, b, c, d, color);
		
	}

	@Override
	public void drawFall(Graphics g) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int randomInt() {		
		return 4 + (int) (Math.random() * 900) + 1;
	}

	@Override
	public boolean checkCollision(int x, int y) {
		// TODO Auto-generated method stub
		return false;
	}
	

}
