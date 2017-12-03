package objects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import images.Images;
import level.Level;

/*
 * THIS CODE WAS MADE BY TREVOR STRICKLAND.
 * DO NOT REDISTRIBUTE, USE FOR EDUCATIONAL
 * OR CRITISISM PURPOSES ONLY. MADE IN DEC 2017,
 * FOR LUDUM DARE 40.
*/

public class Tile {
	
	public static final int AIR = 0;
	public static final int BLOCK = 1;
	public static final int GOAL = 2;
	public static final int COIN = 3;
	public static final int KEY_BLUE = 4;
	public static final int DOOR_BLUE_LOCKED = 5;
	public static final int DOOR_BLUE_UNLOCKED = 6;
	public static final int KEY_GREEN = 7;
	public static final int DOOR_GREEN_LOCKED = 8;
	public static final int DOOR_GREEN_UNLOCKED = 9;
	public static final int KEY_YELLOW = 10;
	public static final int DOOR_YELLOW_LOCKED = 11;
	public static final int DOOR_YELLOW_UNLOCKED = 12;
	public static final int KEY_RED = 13;
	public static final int DOOR_RED_LOCKED = 14;
	public static final int DOOR_RED_UNLOCKED = 15;
	
	public int frames = 0;
	public float coinFrames = 0;
	
	public int x, y, id;
	
	public Rectangle bounds;
	
	public Tile(int x, int y, int id) {
		this.x = x;
		this.y = y;
		this.id = id;
		bounds = new Rectangle(x, y, Level.X_GRIDSIZE, Level.Y_GRIDSIZE);
	}
	
	public boolean isSolid = true;
	
	public void update(Graphics2D g) {
		frames++;
		
		if(frames >= 4) {
			frames = 0;
		}
		
		coinFrames += 0.05f;
		
		if(coinFrames >= 6) {
			coinFrames = 0;
		}
		
		switch(id) {
		case AIR:
			isSolid = false;
			break;
		case BLOCK:
			isSolid = true;
			g.setColor(Color.WHITE);
			g.drawRect(x, y, Level.X_GRIDSIZE, Level.Y_GRIDSIZE);
			break;
		case GOAL:
			isSolid = true;
			g.drawImage(Images.goalFrames[frames], x, y, Level.X_GRIDSIZE, Level.Y_GRIDSIZE, null);
			break;
		case COIN:
			isSolid = false;
			g.drawImage(Images.coins[(int) coinFrames], x, y, Level.X_GRIDSIZE, Level.Y_GRIDSIZE, null);
			break;
		case KEY_BLUE:
			isSolid = true;
			g.drawImage(Images.key[0], x, y, Level.X_GRIDSIZE, Level.Y_GRIDSIZE, null);
			break;
		case DOOR_BLUE_LOCKED:
			isSolid = true;
			g.drawImage(Images.lock[0], x, y, Level.X_GRIDSIZE, Level.Y_GRIDSIZE, null);
			break;
		case DOOR_BLUE_UNLOCKED:
			isSolid = false;
			g.drawImage(Images.lock[1], x, y, Level.X_GRIDSIZE, Level.Y_GRIDSIZE, null);
			break;
		}
	}

}
