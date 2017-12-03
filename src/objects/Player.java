package objects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import audio.AudioClip;
import images.Images;
import level.Level;

/*
 * THIS CODE WAS MADE BY TREVOR STRICKLAND.
 * DO NOT REDISTRIBUTE, USE FOR EDUCATIONAL
 * OR CRITISISM PURPOSES ONLY. MADE IN DEC 2017,
 * FOR LUDUM DARE 40.
*/

public class Player {
	
	public float x, y;
	
	public float velX, velY;
	public float speed = 3;
	
	public boolean isMovingLeft, isMovingRight, isJumping, isFalling,
	isCollidingLeft, isCollidingRight, isCollidingTop, isCollidingBottom,
	canJump, immobilized, isFacingLeft, isFacingRight;
	
	public int key = 0;
	
	public float frames = 0;
	
	public AudioClip jumpClip = new AudioClip("jump", false);
	public AudioClip collectClip = new AudioClip("collect", false);
	
	public Player(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void update(Graphics2D g) {
		g.setColor(Color.BLUE);
		g.drawImage(Images.player[(int) frames], (int) x, (int) y, Level.X_GRIDSIZE, Level.Y_GRIDSIZE, null);
		
		if(isMovingLeft && !isCollidingLeft) {
			velX = -4;
			frames += 0.01f;
			if(frames > 5) {
				frames = 4;
			}
		}else if(isMovingRight && !isCollidingRight) {
			velX = 4;
			frames += 0.01f;
			if(frames > 3) {
				frames = 2;
			}
		}else {
			velX = 0;
		}
		
		if(isFacingLeft) {
			frames = 1;
		}
		if(isFacingRight) {
			frames = 0;
		}
		
		if(isJumping && canJump) {
			jumpClip.play();
			velY = -6;
			velY += 0.05f;
			if(velY >= 0) {
				isFalling = true;
				isJumping = false;
			}
			if(isMovingLeft) {
				frames = 6;
			}
			if(isMovingRight) {
				frames = 7;
			}
		}else if(isJumping && !canJump) { 
			isFalling = true;
			isJumping = false;
		}else if(isFalling) {
			velY += 0.05f;
			if(velY > 5) {
				velY = 5;
			}
			if(isMovingLeft) {
				frames = 6;
			}
			if(isMovingRight) {
				frames = 7;
			}
		}else if(!isFalling && !isJumping){
			velY = 0;
		}
			
		if(isCollidingBottom) {
			isFalling = false;
		}else if(!isCollidingBottom) {
			isFalling = true;
		}
		
		if(isCollidingTop) {
			velY = 0;
			y += 6;
			isFalling = true;
		}
		
		if(immobilized) {
			velX = 0;
			velY = 0;
		}
		
		if(key > 0) {
			g.drawImage(Images.key[key - 1], (int) x, (int) y - (Level.Y_GRIDSIZE / 2), Level.X_GRIDSIZE, Level.Y_GRIDSIZE, null);
		}
		
		x += velX;
		y += velY;
		
		collision();
	}
	
	public void collision() {
		for(int x = 0; x < Level.tiles.length; x++) {
			for(int y = 0; y < Level.tiles[0].length; y++) {
				if(Level.tiles[x][y].bounds.intersects(coinHitBox()) && Level.tiles[x][y].id == Tile.COIN) {
					Level.amountOfCoins++;
					Level.tiles[x][y].id = Tile.AIR;
					collectClip.play();
				}
				
				if(Level.tiles[x][y].bounds.intersects(hitBox()) && Level.tiles[x][y].id == Tile.KEY_BLUE && key == 0) {
					key = 1;
					Level.tiles[x][y].id = Tile.AIR;
				}
				if(Level.tiles[x][y].bounds.intersects(hitBox()) && Level.tiles[x][y].id == Tile.DOOR_BLUE_LOCKED && key == 1) {
					Level.tiles[x][y].id = Tile.DOOR_BLUE_UNLOCKED;
					key = 0;
				}
				
				if((Level.tiles[x][y].bounds.intersects(leftBox()) && Level.tiles[x][y].isSolid) || (Level.tiles[x][y].bounds.intersects(cornerLeftBox()) && Level.tiles[x][y].isSolid)) {
					isCollidingLeft = true;
				}else if((Level.tiles[x][y].bounds.intersects(leftBox()) && !Level.tiles[x][y].isSolid && (Level.tiles[x][y].bounds.intersects(cornerLeftBox()) && !Level.tiles[x][y].isSolid))) {
					isCollidingLeft = false;
				}
				
				if((Level.tiles[x][y].bounds.intersects(rightBox()) && Level.tiles[x][y].isSolid) || (Level.tiles[x][y].bounds.intersects(cornerRightBox()) && Level.tiles[x][y].isSolid)) {
					isCollidingRight = true;
				}else if((Level.tiles[x][y].bounds.intersects(rightBox()) && !Level.tiles[x][y].isSolid) && (Level.tiles[x][y].bounds.intersects(cornerRightBox()) && !Level.tiles[x][y].isSolid)) {
					isCollidingRight = false;
				}
				
				if((Level.tiles[x][y].bounds.intersects(bottomBox()) && Level.tiles[x][y].isSolid) || (Level.tiles[x][y].bounds.intersects(bottomBoxCorner()) && Level.tiles[x][y].isSolid)) {
					isCollidingBottom = true;
					this.y--;
				}else if((Level.tiles[x][y].bounds.intersects(bottomBox()) && !Level.tiles[x][y].isSolid) && (Level.tiles[x][y].bounds.intersects(bottomBoxCorner()) && !Level.tiles[x][y].isSolid)) {
					isCollidingBottom = false;
				}
				
				if((Level.tiles[x][y].bounds.intersects(topBox()) && Level.tiles[x][y].isSolid) || (Level.tiles[x][y].bounds.intersects(topBoxCorner()) && Level.tiles[x][y].isSolid)) {
					isCollidingTop = true;
					isJumping = false;
					isFalling = true;
				}else if((Level.tiles[x][y].bounds.intersects(topBox()) && !Level.tiles[x][y].isSolid) && (Level.tiles[x][y].bounds.intersects(topBoxCorner()) && !Level.tiles[x][y].isSolid)) {
					isCollidingTop = false;
				}
				
				if(Level.tiles[x][y].bounds.intersects(jumpBounds()) && Level.tiles[x][y].isSolid) {
					canJump = true;
				}else if(Level.tiles[x][y].bounds.intersects(jumpBounds()) && !Level.tiles[x][y].isSolid) {
					canJump = false;
				}
			}
		}
	}
	
	public Rectangle leftBox() {
		return new Rectangle((int) x - (Level.X_GRIDSIZE / 2), (int) y + 10, Level.X_GRIDSIZE / 2, (Level.Y_GRIDSIZE) - 20);
	}
	
	public Rectangle cornerLeftBox() {
		return new Rectangle((int) x - (Level.X_GRIDSIZE / 2), (int) y + 10, Level.X_GRIDSIZE / 2, (Level.Y_GRIDSIZE / 2) - 20);
	}
	
	public Rectangle rightBox() {
		return new Rectangle((int) x + (Level.X_GRIDSIZE / 2), (int) y + 10, Level.X_GRIDSIZE / 2, (Level.Y_GRIDSIZE) - 20);
	}
	
	public Rectangle cornerRightBox() {
		return new Rectangle((int) x + (Level.X_GRIDSIZE / 2), (int) y + 10, Level.X_GRIDSIZE / 2, (Level.Y_GRIDSIZE / 2) - 20);
	}
	
	public Rectangle topBox() {
		return new Rectangle((int) x + 5, (int) y, Level.X_GRIDSIZE - 10, 10);
	}
	
	public Rectangle topBoxCorner() {
		return new Rectangle((int) x, (int) y, 10, 10);
	}
	
	public Rectangle bottomBox() {
		return new Rectangle((int) x + 5, (int) y + ((Level.Y_GRIDSIZE) - 10), Level.X_GRIDSIZE - 10, 10);
	}
	
	public Rectangle bottomBoxCorner() {
		return new Rectangle((int) x, (int) y + ((Level.Y_GRIDSIZE) - 10), 10, 10);
	}
	
	public Rectangle hitBox() {
		return new Rectangle((int) x - (Level.X_GRIDSIZE / 2), (int) y, (int) (Level.X_GRIDSIZE * 1.5f), Level.Y_GRIDSIZE);
	}
	
	public Rectangle coinHitBox() {
		return new Rectangle((int) x - 1, (int) y, Level.X_GRIDSIZE + 1, Level.Y_GRIDSIZE);
	}
	
	public Rectangle jumpBounds() {
		return new Rectangle((int) x + 5, (int) y + ((Level.Y_GRIDSIZE) - 10) + 10, (Level.X_GRIDSIZE - 10) / 2, 10);
	}

}
