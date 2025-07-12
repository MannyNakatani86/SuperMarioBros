package objects;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import main.PlayManager;

public class Items {
	
	public BufferedImage image;
	public String name;
	public boolean collision = false;
	public int world_x, world_y;
	
	public void draw(Graphics2D g2, PlayManager pm) {
		int screen_x = world_x - pm.mario.world_x + pm.mario.screen_x;
		int screen_y = world_y;
		
		if(world_x > pm.mario.world_x - pm.mario.screen_x - pm.tileSize && world_x < pm.mario.world_x + pm.mario.screen_x + pm.tileSize * 10) {
			g2.drawImage(image,  screen_x,  screen_y,  pm.tileSize, pm.tileSize, null);
		}
	}
}
