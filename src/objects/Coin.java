package objects;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import images.Images;
import level.Level;

public class Coin {
	
	public float x, y, velX, velY;
	
	public float coinFrames = 0;
	
	public Coin(float x, float y, float velX, float velY) {
		this.x = x;
		this.y = y;
		this.velX = velX;
		this.velY = velY;
	}
	
	public void update(Graphics2D g) {
		coinFrames += 0.05f;
		
		if(coinFrames >= 6) {
			coinFrames = 0;
		}
		
		g.drawImage(Images.coins[(int) coinFrames], (int) x, (int) y, Level.X_GRIDSIZE / 2, Level.Y_GRIDSIZE / 2, null);
		
		x += velX;
		y += velY;
	}
	
	public Rectangle hitBox() {
		return new Rectangle((int) x, (int) y, Level.X_GRIDSIZE / 2, Level.Y_GRIDSIZE / 2);
	}

}
