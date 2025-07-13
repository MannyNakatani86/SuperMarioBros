package objects;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.PlayManager;

public class Mario extends GameObject{

	String form;
	boolean jump, crouch;

	public Mario(PlayManager pm) {
		super(pm);
		screen_x = pm.tileSize * 3;
		screen_y = pm.tileSize * 13 + 1;
		lookRight = true;
		form = "big";
		
		setDefaultValues();
		getImage();
	}
	
	public void setDefaultValues() {
		world_x = pm.tileSize * 3;
		world_y = pm.tileSize * 13 + 1;
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
		}catch(IOException e) {
			e.printStackTrace();
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
	
	private void manageJump() {
		if(jump && onFeet) {
			onFeet = false;
			velY = -36; // initial jumping speed
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
	
	public void update() {
		manageMovement();
		manageJump();
		applyGravity();
		pm.cManager.checkTileCollision(this);
		
		world_x += velX;
		world_y += velY;
		screen_y += velY;
		
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
	
	public void draw(Graphics2D g2) {
		BufferedImage image = null;
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
		}
		
		
		g2.drawImage(image, screen_x, screen_y, pm.tileSize, pm.tileSize, null);
	}
}
