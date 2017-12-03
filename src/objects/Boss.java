package objects;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Random;

import engine.Engine;
import images.Images;
import level.Level;

public class Boss {
	
	public int x, y;
	
	public float degrees = 0;
	
	public int time = 0;
	public int frames = 0;
	
	public Player player;
	
	public Random r = new Random();
	
	public ArrayList<Coin> coins = new ArrayList<Coin>();
	public ArrayList<Key> keys = new ArrayList<Key>();
	
	public Boss(int x, int y, Player player) {
		this.x = x;
		this.y = y;
		this.player = player;
	}
	
	public void update(Graphics2D g) {
		degrees += 0.3f;
		g.rotate(Math.toRadians(degrees), x + 128, y + 128);
		g.drawImage(Images.coins[0], x, y, 256, 256, null);
		g.rotate(Math.toRadians(-degrees), x + 128, y + 128);
		
		time++;
		
		if(time >= 100 && time < 500) {
			frames++;
			if(frames >= 50) {
				coins.add(new Coin(Engine.width / 2, Engine.height / 2, r.nextInt(6) - 3, r.nextInt(6) - 3));
				frames = 0;
			}
		}
		
		if(time >= 500 && time < 750) {
			frames++;
			if(frames >= 50) {
				coins.add(new Coin(Engine.width / 2, Engine.height / 2, (((player.x - Engine.width / 2)) / 100), ((player.y  - Engine.height / 2) / 100)));
				frames = 0;
			}
		}
		
		if(time == 800) {
			keys.add(new Key(Engine.width / 2, Engine.height / 2));
		}
		
		if(time >= 900 && time < 1500) {
			frames++;
			if(frames >= 25) {
				coins.add(new Coin(Engine.width / 2, Engine.height / 2, r.nextInt(6) - 3, r.nextInt(6) - 3));
				frames = 0;
			}
		}
		
		if(time >= 1500 && time < 1800) {
			frames++;
			if(frames >= 25) {
				coins.add(new Coin(Engine.width / 2, Engine.height / 2, (((player.x - Engine.width / 2)) / 100), ((player.y  - Engine.height / 2) / 100)));
				frames = 0;
			}
		}
		
		if(time == 1900) {
			keys.add(new Key(Engine.width / 2, Engine.height / 2));
		}
		
		if(time >= 2000 && time < 2500) {
			frames++;
			if(frames >= 10) {
				coins.add(new Coin(Engine.width / 2, Engine.height / 2, r.nextInt(6) - 3, r.nextInt(6) - 3));
				frames = 0;
			}
		}
		
		if(time >= 2500 && time < 2800) {
			frames++;
			if(frames >= 10) {
				coins.add(new Coin(Engine.width / 2, Engine.height / 2, (((player.x - Engine.width / 2)) / 100), ((player.y  - Engine.height / 2) / 100)));
				frames = 0;
			}
		}
		
		if(time == 2900) {
			keys.add(new Key(Engine.width / 2, Engine.height / 2));
		}
		
		if(time >= 3000 && time < 3500) {
			frames++;
			if(frames >= 5) {
				coins.add(new Coin(Engine.width / 2, Engine.height / 2, r.nextInt(6) - 3, r.nextInt(6) - 3));
				frames = 0;
			}
		}
		
		if(time >= 3500 && time < 3800) {
			frames++;
			if(frames >= 5) {
				coins.add(new Coin(Engine.width / 2, Engine.height / 2, (((player.x - Engine.width / 2)) / 100), ((player.y  - Engine.height / 2) / 100)));
				frames = 0;
			}
		}
		
		if(time == 3900) {
			keys.add(new Key(Engine.width / 2, Engine.height / 2));
		}
		
		System.out.println(time);
		
		for(Coin c : coins) {
			if(c.x < -Level.X_GRIDSIZE / 2 || c.y < -Level.Y_GRIDSIZE / 2 || c.x > Engine.width || c.y > Engine.height) {
				coins.remove(c);
				break;
			}
			if(c.hitBox().intersects(player.hitBox())) {
				Level.amountOfCoins += 1;
				coins.remove(c);
				break;
			}
			c.update(g);
		}
		
		for(Key k : keys) {
			if(k.hitBox().intersects(player.hitBox())) {
				player.key = 1;
				keys.remove(k);
				break;
			}
			k.update(g);
		}
	}

}
