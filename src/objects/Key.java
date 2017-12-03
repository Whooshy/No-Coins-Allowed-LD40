package objects;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import images.Images;
import level.Level;

public class Key {
	
	public float x, y;
	
	public float velY = 0;
	
	public boolean colliding = false;
	
	public Key(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public void update(Graphics2D g) {
		g.drawImage(Images.key[0], (int) x, (int) y, Level.X_GRIDSIZE, Level.Y_GRIDSIZE, null);
		
		for(int x = 0; x < Level.tiles.length; x++) {
			for(int y = 0; y < Level.tiles[0].length; y++) {
				if(Level.tiles[x][y].bounds.intersects(hitBox()) && Level.tiles[x][y].isSolid) {
					colliding = true;
				}else if(Level.tiles[x][y].bounds.intersects(hitBox()) && !Level.tiles[x][y].isSolid){
					colliding = false;
				}
			}
		}
		
		if(!colliding) {
			y += 3;
		}
	}
	
	public Rectangle hitBox() {
		return new Rectangle((int) x, (int) y, Level.X_GRIDSIZE, Level.Y_GRIDSIZE);
	}

}
