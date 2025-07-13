package objects;

import main.PlayManager;

public abstract class GameObject {
	
	PlayManager pm;
	// Location
	public int world_x, world_y;
	public int screen_x, screen_y;
	public int height;
	// Movements
	public int velX, velY;
	public boolean onFeet = true, walk, run, lookRight;
	
	public int spriteCounter = 0;
	public int spriteNum = 1;
	
	public GameObject(PlayManager pm) {
		this.pm = pm;
	}
	
	public abstract void manageLanding(int y);
	
	public void manageUp(int y) {
		world_y = y;
		screen_y = y;
		velY = 0;
	}
	
	public abstract void manageRight(int x);
	
	public abstract void manageLeft(int x);
}
