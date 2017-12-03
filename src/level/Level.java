package level;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import audio.AudioClip;
import engine.Engine;
import images.Images;
import objects.Boss;
import objects.Player;
import objects.Tile;

/*
 * THIS CODE WAS MADE BY TREVOR STRICKLAND.
 * DO NOT REDISTRIBUTE, USE FOR EDUCATIONAL
 * OR CRITISISM PURPOSES ONLY. MADE IN DEC 2017,
 * FOR LUDUM DARE 40.
*/

public class Level {
	
	public static Tile[][] tiles = new Tile[16][11];
	
	public Player player = new Player((Engine.width / 2) - (X_GRIDSIZE / 2), Engine.height - (Y_GRIDSIZE * 3));
	public Boss boss = new Boss((Engine.width / 2) - (128), (Engine.height / 2) - 128, player);
	
	public static int X_GRIDSIZE = Engine.width / 15;
	public static int Y_GRIDSIZE = Engine.height / 10;
	
	public int transitionFrames = Images.transitions.length;
	
	public int level = 0;
	public static float amountOfCoins = 0;
	
	public float time = 15.999f;
	
	public int deathFrames = 0;
	
	public boolean isCollidingWithGoal = false;
	
	public String lvlName;
	
	public AudioClip music = new AudioClip("bg01", true);
	public AudioClip bossMusic = new AudioClip("bg02", true);
	
	public AudioClip loadClip = new AudioClip("load", false);
	
	public Level() {
		for(int x = 0; x < tiles.length; x++) {
			for(int y = 0; y < tiles[0].length; y++) {
				tiles[x][y] = new Tile(x * X_GRIDSIZE, y * Y_GRIDSIZE, Tile.AIR);
			}
		}
		
		generate(level);
		music.play();
	}
	
	public void generate(int level) {
		for(int x = 0; x < Images.levels[level].getWidth(); x++) {
			for(int y = 0; y < Images.levels[level].getHeight(); y++) {
				if(Images.levels[level].getRGB(x, y) == Color.BLACK.getRGB()) {
					tiles[x][y].id = Tile.AIR;
				}
				if(Images.levels[level].getRGB(x, y) == Color.WHITE.getRGB()) {
					tiles[x][y].id = Tile.BLOCK;
				}
				if(Images.levels[level].getRGB(x, y) == Color.GREEN.getRGB()) {
					tiles[x][y].id = Tile.GOAL;
				}
				if(Images.levels[level].getRGB(x, y) == new Color(255, 216, 0).getRGB()) {
					tiles[x][y].id = Tile.COIN;
				}
				if(Images.levels[level].getRGB(x, y) == new Color(0, 200, 255).getRGB()) {
					tiles[x][y].id = Tile.KEY_BLUE;
				}
				if(Images.levels[level].getRGB(x, y) == new Color(0, 255, 255).getRGB()) {
					tiles[x][y].id = Tile.DOOR_BLUE_LOCKED;
				}
			}
		}
	}
	
	public void update(Graphics2D g) {
		for(int x = 0; x < tiles.length; x++) {
			for(int y = 0; y < tiles[0].length; y++) {
				tiles[x][y].update(g);
				if(player.coinHitBox().intersects(tiles[x][y].bounds) && tiles[x][y].id == Tile.GOAL) {
					isCollidingWithGoal = true;
					player.immobilized = true;
				}else if(player.coinHitBox().intersects(tiles[x][y].bounds) && tiles[x][y].id != Tile.GOAL && !isCollidingWithGoal) {
					transitionFrames += 2;
					if(transitionFrames >= Images.transitions.length - 1) {
						transitionFrames = Images.transitions.length - 1;
					}
				}
			}
		}
		
		if(isCollidingWithGoal) {
			transitionFrames -= 2;
			if(transitionFrames <= 0) {
				level += 1;
				if(level < 32) generate(level);
				isCollidingWithGoal = false;
				if(level < 10) time = 15.999f;
				if(level >= 10 && level <= 20) time = 20.999f;
				if(level > 20 && level <= 30) time = 25.999f;
				if(level == 31) { 
					time = 99.999f;
					terminateMusic(music);
					bossMusic.play();
				}
				transitionFrames = 0;
				player.immobilized = false;
				loadClip.play();
			}
		}
		
		time -= ((amountOfCoins + 1) / 7500);
		
		if(time < 0) {
			g.setColor(Color.RED);
			g.setFont(new Font("Verdana", Font.BOLD, 40));
			g.drawString("OUT OF TIME", (Engine.width / 2) - 150, Engine.height / 2 + 20);
			player.immobilized = true;
			deathFrames++;
			if(deathFrames > 200) {
				terminateMusic(music);
				Engine.STATE = 0;
			}
		}
		
		if(player.x < (0 - X_GRIDSIZE)) {
			player.x += Engine.width +X_GRIDSIZE;
		}
		
		if(player.x > Engine.width + X_GRIDSIZE) {
			player.x -= Engine.width + X_GRIDSIZE * 2;
		}
		
		if(player.y > Engine.height + 3) {
			player.y -= Engine.height + (Y_GRIDSIZE) + 3;
		}
		
		if(player.y < 0 - (Level.Y_GRIDSIZE)) {
			player.y += Engine.height + (Y_GRIDSIZE);
		}
		
		player.update(g);
		
		switch(level) {
		case 0:
			lvlName = "Level 0: Don't Collect the Coins";
			break;
		case 1:
			lvlName = "Level 1: Navigation";
			break;
		case 2:
			lvlName = "Level 2: Pillars";
			break;
		case 3:
			lvlName = "Level 3: The Path";
			break;
		case 4:
			lvlName = "Level 4: Jailbreak";
			break;
		case 5:
			lvlName = "Level 5: Arena";
			break;
		case 6:
			lvlName = "Level 6: Divisive Division";
			break;
		case 7:
			lvlName = "Level 7: Two Types of Blockades";
			break;
		case 8:
			lvlName = "Level 8: Spiral";
			break;
		case 9:
			lvlName = "Level 9: Four Corners";
			break;
		case 10:
			lvlName = "Level 10: Locked In";
			break;
		case 11:
			lvlName = "Level 11: Crossroads";
			break;
		case 12:
			lvlName = "Level 12: Double Locked";
			break;
		case 13:
			lvlName = "Level 13: Airship";
			break;
		case 14:
			lvlName = "Level 14: Airlock";
			break;
		case 15:
			lvlName = "Level 15: One Way";
			break;
		case 16:
			lvlName = "Level 16: Surrounded";
			break;
		case 17:
			lvlName = "Level 17: Flarpy Borg";
			break;
		case 18:
			lvlName = "Level 18: Warp Pipe";
			break;
		case 19:
			lvlName = "Level 19: Nineteen";
			break;
		case 20:
			lvlName = "Level 20: Ominous Lack of Coins";
			break;
		case 21:
			lvlName = "Level 21: Discrepency";
			break;
		case 22:
			lvlName = "Level 22: Tight Spaces";
			break;
		case 23:
			lvlName = "Level 23: Keys are Solid";
			break;
		case 24:
			lvlName = "Level 24: Velocity";
			break;
		case 25:
			lvlName = "Level 25: Downstairs (or Upstairs)";
			break;
		case 26:
			lvlName = "Level 26: Floating Islands";
			break;
		case 27:
			lvlName = "Level 27: Four Corners II";
			break;
		case 28:
			lvlName = "Level 28: Dizzy";
			break;
		case 29:
			lvlName = "Level 29: Bank Stash";
			break;
		case 30:
			lvlName = "Level 30: Homestretch";
			break;
		case 31:
			lvlName = "Final Boss";
			boss.update(g);
			break;
		case 32:
			Engine.STATE = 3;
			break;
		}
		
		g.setFont(new Font("Verdana", Font.PLAIN, 30));
		
		g.setColor(Color.YELLOW);
		g.drawString("Coins: " + (int) amountOfCoins, 15, 45);
		
		g.setColor(Color.RED);
		g.drawString("Time: " + (int) time, Engine.width - 185, 45);
		
		g.setColor(Color.WHITE);
		g.drawString(lvlName, 10, Engine.height - 10);
		
		g.drawImage(Images.transitions[transitionFrames], 0, 0, Engine.width, Engine.height, null);
	}
	
	public void terminateMusic(AudioClip clip) {
		clip.clip.stop();
	}

}
