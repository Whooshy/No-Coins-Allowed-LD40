package menu;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import audio.AudioClip;
import engine.Engine;
import images.Images;
import level.Level;

public class MainMenu {
	
	public Engine engine;
	
	public AudioClip selectClip = new AudioClip("select2", false);
	
	public MainMenu(Engine engine) {
		this.engine = engine;
	}
	
	public int selection = 0;
	
	public void update(Graphics2D g) {
		g.drawImage(Images.mainScreen, 0, 0, Engine.width, Engine.height, null);
		
		g.setColor(Color.WHITE);
		g.setFont(new Font("Verdana", Font.PLAIN, 30));
		
		if(selection == 1) g.drawString("> Play <", (Engine.width / 2) - 76, Engine.height - 150);
		if(selection != 1) g.drawString("Play", (Engine.width / 2) - 40, Engine.height - 150);
		
		if(selection == 2) g.drawString("> Help <", (Engine.width / 2) - 76, Engine.height - 110);
		if(selection != 2) g.drawString("Help", (Engine.width / 2) - 40, Engine.height - 110);
		
		if(selection == 3) g.drawString("> Quit <", (Engine.width / 2) - 76, Engine.height - 70);
		if(selection != 3) g.drawString("Quit", (Engine.width / 2) - 40, Engine.height - 70);
		
		if(selection == 1 && Engine.LMB) {
			selectClip.play();
			engine.level = new Level();
			engine.level.amountOfCoins = 0;
			Engine.STATE = 1;
		}
		if(selection == 2 && Engine.LMB) {
			selectClip.play();
			Engine.STATE = 2;
		}
		if(selection == 3 && Engine.LMB) {
			selectClip.play();
			System.exit(0);
		}
	}

}
