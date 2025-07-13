package objects;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.PlayManager;

public class Mario extends GameObject{

	String form;
	boolean jump, crouch;
	public boolean dead;
	public BufferedImage stillR, stillL, jumpR, jumpL, left1, left2, left3, 
	right1, right2, right3, big_stillR, big_stillL, big_jumpR, big_jumpL, big_left1,
	big_left2, big_left3, big_right1, big_right2, big_right3, gone;

	public Mario(PlayManager pm) {
		super(pm);
		screen_x = pm.tileSize * 3;
		screen_y = pm.tileSize * 13 + 1;
		lookRight = true;
		form = "small";
		
		setDefaultValues();
		getImage();
	}
	
	public void setDefaultValues() {
		world_x = pm.tileSize * 3;
		world_y = pm.tileSize * 13 + 1;
		height = pm.tileSize;
	}
	
	public void getImage() {
		try {
			stillR = ImageIO.read(getClass().getResourceAsStream("/mario/mario_stillR.png"));
			stillL = ImageIO.read(getClass().getResourceAsStream("/mario/mario_stillL.png"));
			jumpR = ImageIO.read(getClass().getResourceAsStream("/mario/mario_jumpR.png"));
			jumpL = ImageIO.read(getClass().getResourceAsStream("/mario/mario_jumpL.png"));
			left1 = ImageIO.read(getClass().getResourceAsStream("/mario/mario_moveL1.png"));
			left2 = ImageIO.read(getClass().getResourceAsStream("/mario/mario_moveL2.png"));
			left3 = ImageIO.read(getClass().getResourceAsStream("/mario/mario_moveL3.png"));
			right1 = ImageIO.read(getClass().getResourceAsStream("/mario/mario_moveR1.png"));
			right2 = ImageIO.read(getClass().getResourceAsStream("/mario/mario_moveR2.png"));
			right3 = ImageIO.read(getClass().getResourceAsStream("/mario/mario_moveR3.png"));
			big_stillR = ImageIO.read(getClass().getResourceAsStream("/mario/bigMario_stillR.png"));
			big_stillL = ImageIO.read(getClass().getResourceAsStream("/mario/bigMario_stillL.png"));
			big_jumpR = ImageIO.read(getClass().getResourceAsStream("/mario/bigMario_jumpR.png"));
			big_jumpL = ImageIO.read(getClass().getResourceAsStream("/mario/bigMario_jumpL.png"));
			big_left1 = ImageIO.read(getClass().getResourceAsStream("/mario/bigMario_moveL1.png"));
			big_left2 = ImageIO.read(getClass().getResourceAsStream("/mario/bigMario_moveL2.png"));
			big_left3 = ImageIO.read(getClass().getResourceAsStream("/mario/bigMario_moveL3.png"));
			big_right1 = ImageIO.read(getClass().getResourceAsStream("/mario/bigMario_moveR1.png"));
			big_right2 = ImageIO.read(getClass().getResourceAsStream("/mario/bigMario_moveR2.png"));
			big_right3 = ImageIO.read(getClass().getResourceAsStream("/mario/bigMario_moveR3.png"));
			gone = ImageIO.read(getClass().getResourceAsStream("/mario/mario_dead.png"));
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void levelDown() {
		if(form.equals("big")){
			form = "small";
			world_y += pm.tileSize;
			height = pm.tileSize;
		}else if(form.equals("fire")) {
			form = "big";
		}else if(form.equals("small")){
			System.out.println("dead");
			dead = true;
		}
	}
	
	public void walkRightRequest() {
		lookRight = true;
		walk = true;
		velX = 3;
	}
	
	public void walkLeftRequest() {
		lookRight = false;
		walk = true;
		velX = 3;
	}
	
	public void runRightRequest() {
		lookRight = true;
		run = true;
	}
	
	public void runLeftRequest() {
		lookRight = false;
		run = true;
	}
	
	public void jumpRequest() {
		jump = true;
	}
	
	public void crouchRequest() {
		crouch = true;
	}
	
	public void stop() {
		run = false;
		jump = false;
		crouch = false;
		velX = 0;
	}
	
	public void manageLanding(int y) {
		world_y = y - height + 1;
		onFeet = true;
		velY = 0;
	}

	public void manageRight(int x) {
		world_x = x;
		velX = 0;
	}
	
	public void manageLeft(int x) {
		world_x = x;
		velX = 0;
	}
	
	private void manageMovement() {
		if(run) {
			if(lookRight) {
				velX = 5; // running speed
			}else {
				velX = -5;
			}
		}else if(walk) {
			if(lookRight) {
				velX = 3; // walking speed
			}else {
				velX = -3;
			}
		}
	}
	
	public void littleJump() {
		velY = -20;
		pm.playSE(3);
	}
	
	private void manageJump() {
		if(jump && onFeet) {
			onFeet = false;
			velY = -36; // initial jumping speed
			pm.playSE(1);
		}
		jump = false;
	}
	
	private void applyGravity() {		
		if(velY <= 0) {
			velY += 3; // gravity when jumping upwards
		}else {
			velY += 5; // gravity when going downwards
			if(velY > 18) {
				velY = 18; // max falling speed
			}
		}
		if(onFeet) {
			velY = 0;
		}
	}
	
	private void deadAnimationJump() {
		onFeet = false;
		velY = -20;
	}
	
	private void deadAnimationGravity() {
		velY += 1;
	}
	
	public void update() {
		if(dead) {
			if(deadAnimationCounter == 0) {
				deadAnimationJump();
				pm.playSE(2);
			}
			deadAnimationGravity();
			deadAnimationCounter++;
			world_y += velY;
			screen_y = world_y;
			if(deadAnimationCounter >= 100) {
				pm.gameOver = true;
			}
		}else {
			manageMovement();
			manageJump();
			applyGravity();
			pm.cManager.checkTileCollision(this);
			for(int i = 0; i < pm.enemies.length; i++) {
				pm.cManager.checkEnemyCollision(this, pm.enemies[i]);
			}
			
			world_x += velX;
			world_y += velY;
			screen_y = world_y;
			
			spriteCounter++;
			if(spriteCounter > 5) {
				if(spriteNum == 1) {
					spriteNum = 2;
				}else if(spriteNum == 2) {
					spriteNum = 3;
				}else if(spriteNum == 3) {
					spriteNum = 1;
				}
				spriteCounter = 0;
			}
		}
	}
	
	public void draw(Graphics2D g2) {
		BufferedImage image = null;
		if(dead) {
			image = gone;
		}else {
			if(form.equals("small")) {
				if(lookRight) {
					if(!onFeet){
						image = jumpR;
					}else if(run) {
						if(spriteNum == 1) {
							image = right1;
						}
						if(spriteNum == 2) {
							image = right2;
						}
						if(spriteNum == 3) {
							image = right3;
						}
					}else {
						image = stillR;
					}
				}else {
					if(!onFeet) {
						image = jumpL;
					}else if(run) {
						if(spriteNum == 1) {
							image = left1;
						}
						if(spriteNum == 2) {
							image = left2;
						}
						if(spriteNum == 3) {
							image = left3;
						}
					}else {
						image = stillL;
					}
				}
			}else if(form.equals("big")) {
				if(lookRight) {
					if(!onFeet){
						image = big_jumpR;
					}else if(run) {
						if(spriteNum == 1) {
							image = big_right1;
						}
						if(spriteNum == 2) {
							image = big_right2;
						}
						if(spriteNum == 3) {
							image = big_right3;
						}
					}else {
						image = big_stillR;
					}
				}else {
					if(!onFeet) {
						image = big_jumpL;
					}else if(run) {
						if(spriteNum == 1) {
							image = big_left1;
						}
						if(spriteNum == 2) {
							image = big_left2;
						}
						if(spriteNum == 3) {
							image = big_left3;
						}
					}else {
						image = big_stillL;
					}
				}
			}
		}
		g2.drawImage(image, screen_x, screen_y, pm.tileSize, height, null);
	}
}
