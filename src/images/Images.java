package images;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

/*
 * THIS CODE WAS MADE BY TREVOR STRICKLAND.
 * DO NOT REDISTRIBUTE, USE FOR EDUCATIONAL
 * OR CRITISISM PURPOSES ONLY. MADE IN DEC 2017,
 * FOR LUDUM DARE 40.
*/

public class Images {
	
	public static BufferedImage[] levels = new BufferedImage[32];
	
	public static BufferedImage goalSheet;
	public static BufferedImage[] goalFrames = new BufferedImage[4];
	
	public static BufferedImage coinSheet;
	public static BufferedImage[] coins = new BufferedImage[6];
	
	public static BufferedImage transitionSheet;
	public static BufferedImage[] transitions = new BufferedImage[100];
	
	public static BufferedImage keySheet;
	public static BufferedImage[] key = new BufferedImage[4];
	
	public static BufferedImage lockSheet;
	public static BufferedImage[] lock = new BufferedImage[4];
	
	public static BufferedImage playerSheet;
	public static BufferedImage[] player = new BufferedImage[8];
	
	public static BufferedImage mainScreen;
	public static BufferedImage helpScreen;
	public static BufferedImage endScreen;
	
	public Images() {
		try {
			goalSheet = ImageIO.read(getClass().getResource("/tiles/goalSheet.png"));
			coinSheet = ImageIO.read(getClass().getResource("/tiles/coinSheet.png"));
			keySheet = ImageIO.read(getClass().getResource("/tiles/keySheet.png"));
			lockSheet = ImageIO.read(getClass().getResource("/tiles/lockSheet.png"));
			transitionSheet = ImageIO.read(getClass().getResource("/effects/transition_sheet.png"));
			playerSheet = ImageIO.read(getClass().getResource("/player/playerSheet.png"));
			mainScreen = ImageIO.read(getClass().getResource("/menu/mainScreen.png"));
			helpScreen = ImageIO.read(getClass().getResource("/menu/helpScreen.png"));
			endScreen = ImageIO.read(getClass().getResource("/menu/endScreen.png"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		for(int i = 0; i < levels.length; i++)
			try {
				levels[i] = ImageIO.read(getClass().getResource("/levels/lvl_" + i + ".png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		for(int i = 0; i < goalFrames.length; i++)
			goalFrames[i] = goalSheet.getSubimage(i * 64, 0, 64, 64);
		for(int i = 0; i < transitions.length; i++)
			transitions[i] = transitionSheet.getSubimage(i, 0, 1, 1);
		for(int i = 0; i < coins.length; i++)
			coins[i] = coinSheet.getSubimage(i * 64, 0, 64, 64);
		for(int i = 0; i < key.length; i++)
			key[i] = keySheet.getSubimage(i * 64, 0, 64, 64);
		for(int i = 0; i < lock.length; i++)
			lock[i] = lockSheet.getSubimage(i * 64, 0, 64, 64);
		for(int i = 0; i < player.length; i++)
			player[i] = playerSheet.getSubimage(i * 64, 0, 64, 64);
	}

}
