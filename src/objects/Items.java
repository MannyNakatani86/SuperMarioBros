package objects;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import main.PlayManager;

public abstract class Items {
	
	PlayManager pm;
	public BufferedImage image;
	public String name;
	public int hitCount = 0;
	public boolean collision = false, visible = false, onFeet;
	public int animationCounter = 0;
	public int world_x, world_y, velX = 0, velY = 0;
	
	public Items(PlayManager pm) {
		this.pm = pm;
	}
	
	public abstract void hitAnimation();
	
	public void draw(Graphics2D g2, PlayManager pm) {
		int screen_x = world_x - pm.mario.world_x + pm.mario.screen_x;
		int screen_y = world_y;
		
		if(world_x > pm.mario.world_x - pm.mario.screen_x - pm.tileSize && world_x < pm.mario.world_x + pm.mario.screen_x + pm.tileSize * 10) {
			if(visible) {
				g2.drawImage(image,  screen_x,  screen_y,  pm.tileSize, pm.tileSize, null);
			}
		}
	}
	
	public abstract void update();
}
