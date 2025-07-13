package objects;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.PlayManager;

public class KoopaTroopa extends Enemy{

	public BufferedImage image1, image2;

	public KoopaTroopa(PlayManager pm) {
		super(pm);
		lookRight = false;
		
		getImage();
	}
	
	public void gotHit() {
		
	}
	
	private void manageMovement() {
		if(lookRight) {
			velX = 1;
		}else {
			velX = -1;
		}
	}
	
	public void manageLanding(int y) {
		world_y = y - height + 1;
		screen_y = world_y;
		onFeet = true;
		velY = 0;
	}
	
	public void manageRight(int x) {
		world_x = x;
		lookRight = false;
	}
	
	public void manageLeft(int x) {
		world_x = x;
		lookRight = true;
	}
	
	public void getImage() {
		try {
			// have to fix these images
			image1 = ImageIO.read(getClass().getResourceAsStream("/enemy/koopaTroopaL.png"));
			image2 = ImageIO.read(getClass().getResourceAsStream("/enemy/koopaTroopaL2.png"));
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void update() {
		if(world_x > pm.mario.world_x - pm.mario.screen_x - pm.tileSize && world_x < pm.mario.world_x + pm.mario.screen_x + pm.tileSize * 10 ) {
			manageMovement();
			applyGravity();
			pm.cManager.checkTileCollision(this);
			
			world_x += velX;
			world_y += velY;
			screen_x += velX;
			screen_y += velY;
			
			spriteCounter++;
			if(spriteCounter > 10) {
				if(spriteNum == 1) {
					spriteNum = 2;
				}else if(spriteNum == 2) {
					spriteNum = 1;
				}
				spriteCounter = 0;
			}
		}
	}
	
	public void draw(Graphics2D g2, PlayManager pm) {
		BufferedImage image = null;
		if(spriteNum == 1) {
			image = image1;
		}else if(spriteNum == 2){
			image = image2;
		}
		int screen_x = world_x - pm.mario.world_x + pm.mario.screen_x;
		int screen_y = world_y;
		if(world_x > pm.mario.world_x - pm.mario.screen_x - pm.tileSize && world_x < pm.mario.world_x + pm.mario.screen_x + pm.tileSize * 10) {
		//	g2.drawImage(image,  screen_x,  screen_y,  pm.tileSize, pm.tileSize * 2, null);
		}
	}
}
