package objects;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import main.PlayManager;

public abstract class Enemy extends GameObject{
	
	public BufferedImage image;
	public boolean canMove = false, dead, stepped;

	public Enemy(PlayManager pm) {
		super(pm);
	}
	
	public void applyGravity() {
		velY += 5; // gravity when going downwards
		if(velY > 18) {
			velY = 18; // max falling speed
		}
		
		if(onFeet) {
			velY = 0;
		}
	}
	
	public abstract void gotHit();
	public abstract void draw(Graphics2D g2, PlayManager playManager);
	public abstract void update();
}
