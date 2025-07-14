package objects;

import java.io.IOException;

import javax.imageio.ImageIO;

import main.PlayManager;

public class Mushroom extends Items{

	public Mushroom(PlayManager pm) {
		super(pm);
		name = "mushroom";
		height = pm.tileSize;
		lookRight = true;
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/objects/mushroom.png"));
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	private void manageMovement() {
		if(lookRight) {
			velX = 3;
		}else {
			velX = -3;
		}
	}
	
	public void manageLanding(int y) {
		world_y = y - pm.tileSize + 1;
		onFeet = true;
		velY = 0;
	}
	
	public void update() {
		if(hitCount == 1) {
			if(animationCounter == 0) {
				pm.playSE(9);
			}
			if(animationCounter <= 47) {
				hitAnimation();
			}else {
				if(animationCounter == 48) {
					canGetHit = true;
					velY = 0;
				}
				manageMovement();
				applyGravity();
				pm.cManager.checkTileCollision(this);
				for(int i = 0; i < pm.enemies.length; i++) {
					pm.cManager.checkEnemyCollision(this, pm.enemies[i]);
				}
			}
		}else if(hitCount == 2) {
			dead = true;
			pm.mario.levelUp(name);
		}
		world_x += velX;
		world_y += velY;
	}

	public void manageRight(int x) {
		world_x = x;
		lookRight = false;
	}

	public void manageLeft(int x) {
		world_x = x;
		lookRight = true;
	}
}
