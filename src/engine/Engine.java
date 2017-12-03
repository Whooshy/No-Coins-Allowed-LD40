package engine;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import audio.AudioClip;
import images.Images;
import level.Level;
import menu.MainMenu;

/*
 * THIS CODE WAS MADE BY TREVOR STRICKLAND.
 * DO NOT REDISTRIBUTE, USE FOR EDUCATIONAL
 * OR CRITISISM PURPOSES ONLY. MADE IN DEC 2017,
 * FOR LUDUM DARE 40.
*/

public class Engine extends Canvas implements Runnable, KeyListener, MouseListener, MouseMotionListener {
	
	private Thread thread;
	
	public boolean running = false;
	
	public int frameCount = 0;
	public int FPS = 0;
	
	public static int width = 1280;
	public static int height = 720;
	
	public AudioClip selectClip = new AudioClip("select", false);
	
	public Images imgs;
	public Level level;
	public MainMenu menu;
	
	public int mouseX, mouseY;
	
	public static boolean LMB = false;
	
	public static int STATE = 0;
	
	public Engine() {
		width = 1200;
		height = 800;
		
		JFrame frame = new JFrame("No Coins Allowed");
		
		this.setPreferredSize(new Dimension(width, height));
		
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setLayout(new BorderLayout());
		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
		
		frame.add(this, BorderLayout.CENTER);
		this.addKeyListener(this);
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		
		frame.pack();
		frame.setLocationRelativeTo(null);
		
		imgs = new Images();

		menu = new MainMenu(this);
		
		level.X_GRIDSIZE = (int) (width / 15);
		level.Y_GRIDSIZE = (int) (height / 10);
		
		start();
	}
	
	public static void main(String[] args) {
		new Engine();
	}
	
	public synchronized void start() {
		running = true;
		
		thread = new Thread(this);
		thread.start();
	}
	
	public synchronized void stop() {
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		long timer = System.currentTimeMillis();
		while(running) {
			try {
				thread.sleep(7);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			frameCount++;
			
			update();
			
			if(System.currentTimeMillis() - timer >= 1000) {
				FPS = frameCount;
				System.out.println("FPS: " + FPS);
				frameCount = 0;
				timer += 1000;
			}
		}
	}
	
	public void update() {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			createBufferStrategy(3);
			return;
		}
		Graphics graphics = bs.getDrawGraphics();
		Graphics2D g = (Graphics2D) graphics;
		
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, width, height);
		
		if(STATE == 0) menu.update(g);
		if(STATE == 1) level.update(g);
		if(STATE == 2) g.drawImage(Images.helpScreen, 0, 0, Engine.width, Engine.height, null);
		if(STATE == 3) g.drawImage(Images.endScreen, 0, 0, Engine.width, Engine.height, null);
		
		graphics.dispose();
		bs.show();
	}

	public void keyPressed(KeyEvent e) {
		int k = e.getKeyCode();
		
		if(k == e.VK_A) {
			level.player.isMovingLeft = true;
			level.player.isMovingRight = false;
			level.player.isFacingLeft = false;
			level.player.isFacingRight = false;
		}
		if(k == e.VK_D) {
			level.player.isMovingLeft = false;
			level.player.isMovingRight = true;
			level.player.isFacingLeft = false;
			level.player.isFacingRight = false;
		}
		
		if(k == e.VK_SPACE && level.player.canJump) {
			level.player.isJumping = true;
			level.player.isFacingLeft = false;
			level.player.isFacingRight = false;
		}
	}

	public void keyReleased(KeyEvent e) {
		int k = e.getKeyCode();
		
		if(k == e.VK_ESCAPE) {
			if(STATE == 0) System.exit(0);
			if(STATE == 2) STATE = 0;
			if(STATE == 3) { 
				level.terminateMusic(level.bossMusic);
				STATE = 0;
			}
			if(STATE == 1) {
				if(level.level < 31) level.terminateMusic(level.music);
				if(level.level == 31) level.terminateMusic(level.bossMusic);
				STATE = 0;
			}
		}
		
		if(k == e.VK_A) {
			level.player.isMovingLeft = false;
			level.player.isFacingLeft = true;
			level.player.isFacingRight = false;
		}
		if(k == e.VK_D) {
			level.player.isMovingRight = false;
			level.player.isFacingLeft = false;
			level.player.isFacingRight = true;
		}
		if(k == e.VK_SPACE) {
			level.player.isJumping = false;
			level.player.isFalling = true;
		}
	}

	public void keyTyped(KeyEvent e) {}

	public void mouseDragged(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
	}

	public void mouseMoved(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		
		mouseX = x;
		mouseY = y;
		
		if(STATE == 0) {
			if(mouseY > Engine.height - 180 && mouseY < Engine.height - 150) {
				menu.selection = 1;
				if(mouseY == Engine.height - 180 + 1) selectClip.play();
				if(mouseY == Engine.height - 150) selectClip.play();
			}else if(mouseY > Engine.height - 140 && mouseY < Engine.height - 110) {
				menu.selection = 2;
				if(mouseY == Engine.height - 140 + 1) selectClip.play();
				if(mouseY == Engine.height - 110) selectClip.play();
			}else if(mouseY > Engine.height - 100 && mouseY < Engine.height - 70) {
				menu.selection = 3;
				if(mouseY == Engine.height - 100 + 1) selectClip.play();
				if(mouseY == Engine.height - 70) selectClip.play();
			}else if(mouseY > Engine.height - 180 || mouseY < Engine.height - 70){
				//menu.selection = 0;
			}
		}
	}

	public void mouseClicked(MouseEvent e) {

	}

	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}

	public void mousePressed(MouseEvent e) {
		LMB = true;
	}

	public void mouseReleased(MouseEvent e) {
		LMB = false;
	}
}
