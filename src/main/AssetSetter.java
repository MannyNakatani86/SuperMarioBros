package main;

import objects.Goomba;
import objects.KoopaTroopa;
import objects.Mushroom;

public class AssetSetter {

	PlayManager pm;
	
	public AssetSetter(PlayManager pm) {
		this.pm = pm;
	}
	
	public void setItems() {
		pm.items[0] = new Mushroom();
		pm.items[0].world_x = 21 * pm.tileSize;
		pm.items[0].world_y = 10 * pm.tileSize;
	}
	
	public void setEnemies() {
		pm.enemies[0] = new Goomba(pm); 
		pm.enemies[0].world_x = 23 * pm.tileSize;
		pm.enemies[0].world_y = 13 * pm.tileSize + 1;
		
		pm.enemies[1] = new Goomba(pm);
		pm.enemies[1].world_x = 41 * pm.tileSize;
		pm.enemies[1].world_y = 13 * pm.tileSize + 1;
		
		pm.enemies[2] = new Goomba(pm);
		pm.enemies[2].world_x = 52 * pm.tileSize;
		pm.enemies[2].world_y = 13 * pm.tileSize + 1;
		
		pm.enemies[3] = new Goomba(pm);
		pm.enemies[3].world_x = 54 * pm.tileSize;
		pm.enemies[3].world_y = 13 * pm.tileSize + 1;
		
		pm.enemies[4] = new Goomba(pm);
		pm.enemies[4].world_x = 81 * pm.tileSize;
		pm.enemies[4].world_y = 5 * pm.tileSize + 1;
		
		pm.enemies[5] = new Goomba(pm);
		pm.enemies[5].world_x = 83 * pm.tileSize;
		pm.enemies[5].world_y = 5 * pm.tileSize + 1;
		
		pm.enemies[6] = new Goomba(pm);
		pm.enemies[6].world_x = 98 * pm.tileSize;
		pm.enemies[6].world_y = 13 * pm.tileSize + 1;
		
		pm.enemies[7] = new Goomba(pm);
		pm.enemies[7].world_x = 100 * pm.tileSize;
		pm.enemies[7].world_y = 13 * pm.tileSize + 1;
		
		pm.enemies[8] = new Goomba(pm); // should be koopa troopa
		pm.enemies[8].world_x = 108 * pm.tileSize;
		pm.enemies[8].world_y = 12 * pm.tileSize + 1;
		
		pm.enemies[9] = new Goomba(pm);
		pm.enemies[9].world_x = 115 * pm.tileSize;
		pm.enemies[9].world_y = 13 * pm.tileSize + 1;
		
		pm.enemies[10] = new Goomba(pm);
		pm.enemies[10].world_x = 117 * pm.tileSize;
		pm.enemies[10].world_y = 13 * pm.tileSize + 1;
		
		pm.enemies[11] = new Goomba(pm);
		pm.enemies[11].world_x = 125 * pm.tileSize;
		pm.enemies[11].world_y = 13 * pm.tileSize + 1;
		
		pm.enemies[12] = new Goomba(pm);
		pm.enemies[12].world_x = 127 * pm.tileSize;
		pm.enemies[12].world_y = 13 * pm.tileSize + 1;
		
		pm.enemies[13] = new Goomba(pm);
		pm.enemies[13].world_x = 130 * pm.tileSize;
		pm.enemies[13].world_y = 13 * pm.tileSize + 1;
		
		pm.enemies[14] = new Goomba(pm);
		pm.enemies[14].world_x = 132 * pm.tileSize;
		pm.enemies[14].world_y = 13 * pm.tileSize + 1;
		
		pm.enemies[15] = new Goomba(pm);
		pm.enemies[15].world_x = 175 * pm.tileSize;
		pm.enemies[15].world_y = 13 * pm.tileSize + 1;
		
		pm.enemies[16] = new Goomba(pm);
		pm.enemies[16].world_x = 177 * pm.tileSize;
		pm.enemies[16].world_y = 13 * pm.tileSize + 1;
	}
}
