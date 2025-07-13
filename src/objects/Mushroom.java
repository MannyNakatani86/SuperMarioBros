package objects;

import java.io.IOException;

import javax.imageio.ImageIO;

import main.PlayManager;

public class Mushroom extends Items{

	public Mushroom(PlayManager pm) {
		super(pm);
		name = "mushroom";
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/objects/mushroom.png"));
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void hitAnimation() {
		if(animationCounter <= 11) {
			world_y -= 4;
		}else {
			world_x += 4;
		}
		
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
	
	public void manageLanding(int y) {
		world_y = y - pm.tileSize + 1;
		onFeet = true;
		velY = 0;
	}
	
	public void update() {
		if(hitCount == 1) {
			hitAnimation();
			animationCounter++;
			applyGravity();
		}
	}
}
