package audio;

import java.io.BufferedInputStream;
import java.io.InputStream;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/*
 * THIS CODE WAS MADE BY TREVOR STRICKLAND.
 * DO NOT REDISTRIBUTE, USE FOR EDUCATIONAL
 * OR CRITISISM PURPOSES ONLY. MADE IN DEC 2017,
 * FOR LUDUM DARE 40.
*/

public class AudioClip implements Runnable{
	
	public String name;
	
	public Thread thread;
	
	public Clip clip;
	public boolean loop = false;
	
	public AudioClip(String name, boolean loop) {
		this.name = name;
		this.loop = loop;
	}
	
	public void play() {
		thread = new Thread(this);
		thread.start();
	}

	public void run() {
		try {
			InputStream inputStream = getClass().getResourceAsStream("/audio/" + name + ".wav");
			BufferedInputStream bis = new BufferedInputStream(inputStream);
			clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(bis));
			clip.start();
			if(loop) clip.loop(clip.LOOP_CONTINUOUSLY);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	

}
