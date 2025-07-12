package objects;

import java.awt.image.BufferedImage;

import main.PlayManager;

public abstract class GameObject {
	
	PlayManager pm;
	// Location
	public int world_x, world_y;
	public int screen_x, screen_y;
	// Movements
	public int velX, velY;
	
	public BufferedImage stillR, stillL, jumpR, jumpL, left1, left2, left3, right1, right2, right3;
	public boolean onFeet = true, walk, run, lookRight;
	
	public int spriteCounter = 0;
	public int spriteNum = 1;
	
	public GameObject(PlayManager pm) {
		this.pm = pm;
	}
	
	public void manageLanding(int y) {
		world_y = y + 1;
		screen_y = y + 1;
		onFeet = true;
		velY = 0;
	}
	
	public void manageUp(int y) {
		world_y = y;
		screen_y = y;
		velY = 0;
	}
	
	public abstract void manageRight(int x);
	
	public abstract void manageLeft(int x);
}
