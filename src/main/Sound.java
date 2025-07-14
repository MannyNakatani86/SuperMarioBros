package main;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound {
	
	Clip musicClip;
	Clip seClip;
	URL soundURL[] = new URL[12];
	
	public Sound() {
		soundURL[0] = getClass().getResource("/sound/background.wav");
		soundURL[1] = getClass().getResource("/sound/jump.wav");
		soundURL[2] = getClass().getResource("/sound/dead.wav");
		soundURL[3] = getClass().getResource("/sound/stomp.wav");
		soundURL[4] = getClass().getResource("/sound/coin.wav");
		soundURL[5] = getClass().getResource("/sound/fireball.wav");
		soundURL[6] = getClass().getResource("/sound/superMushroom.wav");
		soundURL[7] = getClass().getResource("/sound/oneUp.wav");
		soundURL[8] = getClass().getResource("/sound/stageComplete.wav");
		soundURL[9] = getClass().getResource("/sound/superMushroom_appear.wav");
		soundURL[10] = getClass().getResource("/sound/pipe_powerDown.wav");
		soundURL[11] = getClass().getResource("/sound/break_block.wav");
	}
	
	public void setMusicFile(int i) {
		try {
			AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
			musicClip = AudioSystem.getClip();
			musicClip.open(ais);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void playMusic() {
		musicClip.start();
	}
	
	public void loop() {
		musicClip.loop(Clip.LOOP_CONTINUOUSLY);
	}
	
	public void stopMusic() {
		musicClip.stop();
	}
	
	public void setSEFile(int i) {
		try {
			AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
			seClip = AudioSystem.getClip();
			seClip.open(ais);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void playSE() {
		seClip.start();
	}
	
	public void stopSE() {
		seClip.stop();
	}
}
