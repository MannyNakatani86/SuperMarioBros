package objects;

import java.io.IOException;

import javax.imageio.ImageIO;

public class Mushroom extends Items{

	public Mushroom() {
		name = "mushroom";
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/objects/mushroom.png"));
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
}
