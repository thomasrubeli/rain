package game_screen;

import java.awt.Color;
import java.awt.Graphics;

public class DeathLine extends FallingObject {

	public DeathLine(int a, int b, int c, int d, Color color) {
		super(a, b, c, d, color);
	}
	
	public void drawFall(Graphics g) {
		if (newLevel) {
			int x = randomInt();
			int y = randomInt();
			this.previousX = x;
			this.currentX = x;
			this.previousY = y - 1000;
			this.currentY = y - 1000;
			newLevel = false;

		}

		g.setColor(color);
		g.drawLine(previousX, previousY, currentX, currentY);
		previousX = currentX;
		currentX += 0;
		previousY = currentY - 5;
		currentY += speed;
		if (!nextLevel) {
			if (currentY > 800) {
				int x = randomInt();
				this.previousX = x;
				this.currentX = x;
				this.previousY = -30;
				this.currentY = -30;
			}
		}

	}

	public int randomInt() {
		return 4 + (int) (Math.random() * 900) + 1;
	}

	public boolean checkCollision(int x, int y) {
		if (((x <= currentX) && ((x + (GamePanel.sphere_cons / 2)) >= currentX))
				&& (y >= currentY && (y - (GamePanel.sphere_cons / 2) <= currentY))
				|| (((x >= currentX) && ((x - (GamePanel.sphere_cons / 2)) <= currentX)) && (y >= currentY && (y
						- (GamePanel.sphere_cons / 2) <= currentY)))
				|| (((x >= currentX) && ((x - (GamePanel.sphere_cons / 2)) <= currentX)) && (y <= currentY && (y
						+ (GamePanel.sphere_cons / 2) >= currentY)))
				|| (((x <= currentX) && ((x + (GamePanel.sphere_cons / 2)) >= currentX)) && (y <= currentY && (y
						+ (GamePanel.sphere_cons / 2) >= currentY)))) {
			if (color.equals(Color.blue) || color.equals(Color.red)) {
				return true;
			} else if (color.equals(Color.green)) {
				return true;
			}
			return true;
		} else
			return false;

	}

}
