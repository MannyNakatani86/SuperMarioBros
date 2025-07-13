package objects;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.PlayManager;

public class Goomba extends Enemy{
	
	public BufferedImage image1, image2, image3;

	public Goomba(PlayManager pm) {
		super(pm);
		lookRight = false;
		height = pm.tileSize;
		
		getImage();
	}
	
	public void gotHit() {
		stepped = true;
	}
	
	private void manageMovement() {
		if(canMove) {
			if(lookRight) {
				velX = 1;
			}else {
				velX = -1;
			}
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
			image1 = ImageIO.read(getClass().getResourceAsStream("/enemy/goomba1.png"));
			image2 = ImageIO.read(getClass().getResourceAsStream("/enemy/goomba2.png"));
			image3 = ImageIO.read(getClass().getResourceAsStream("/enemy/goomba3.png"));
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void update() {
		if(stepped) {
			deadAnimationCounter++;
			if(deadAnimationCounter == 7) {
				dead = true;
			}
		}else {
			if(world_x < pm.mario.world_x + pm.mario.screen_x + pm.tileSize * 14) {
				canMove = true;
			}
			manageMovement();
			applyGravity();
			pm.cManager.checkTileCollision(this);
			for(int i = 0; i < pm.enemies.length; i++) {
				pm.cManager.checkEnemyCollision(this, pm.enemies[i]);
			}
			
			world_x += velX;
			world_y += velY;
			screen_x += velX;
			screen_y -= velY;
			
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
		if(stepped) {
			image = image3;
		}else {
			if(spriteNum == 1) {
				image = image1;
			}else if(spriteNum == 2){
				image = image2;
			}
		}
		int screen_x = world_x - pm.mario.world_x + pm.mario.screen_x;
		int screen_y = world_y;
		if(world_x > pm.mario.world_x - pm.mario.screen_x - pm.tileSize && world_x < pm.mario.world_x + pm.mario.screen_x + pm.tileSize * 10) {
			g2.drawImage(image,  screen_x,  screen_y,  pm.tileSize, height, null);
		}
	}

}
