package main;

import objects.GameObject;
import objects.Mario;

public class CollisionManager {
	
	private PlayManager pm;

	public CollisionManager(PlayManager pm) {
		this.pm = pm;
	}
	
	public void checkTileCollision(GameObject obj) {
		int col = 0, row = 0;
		boolean bottomCollision = false;
		while(col < pm.maxWorldCol && row < pm.maxScreenRow) {
			while(col < pm.maxWorldCol) {
				if(isOverlappingTile(obj, col, row)) {
					if(isCollidingTileFromTop(obj, row)) {
						bottomCollision = true;
						obj.manageLanding((row - 1) * pm.tileSize);
					}
					if(isCollidingTileFromBottom(obj, row)) {
						obj.manageUp((row + 1) * pm.tileSize);
						if(pm.tManager.mapTileNum[col][row] == 3) {
							pm.tManager.mapTileNum[col][row] = 18; // if question block, change to empty block
						}
						if(pm.tManager.mapTileNum[col][row] == 2) {
							pm.tManager.mapTileNum[col][row] = 0; // if normal block, change to sky
						}
					}else {
						if(isCollidingTileFromLeft(obj, col, row)) {
							obj.manageRight((col - 1) * pm.tileSize + 9);
						}
						if(isCollidingTileFromRight(obj, col, row)) {
							obj.manageLeft((col + 1) * pm.tileSize);
						}
					}
				}
				col++;
			}
			if(col == pm.maxWorldCol) {
				col = 0;
				row++;
			}
		}
		if(!bottomCollision) {
			obj.onFeet = false;
		}
	}
	
	private boolean isOverlappingTile(GameObject obj, int col, int row) {
		int num = pm.tManager.mapTileNum[col][row];
		if(pm.tManager.tile[num].collision) {
			if(obj instanceof Mario) {
				if(obj.world_x + pm.tileSize - 9 + obj.velX > col * pm.tileSize &&
						obj.world_x + obj.velX < (col + 1) * pm.tileSize &&
						obj.world_y + pm.tileSize + obj.velY > row * pm.tileSize &&
						obj.world_y + obj.velY < (row + 1) * pm.tileSize) {
					return true;
				}
			}else {
				if(obj.world_x + pm.tileSize + obj.velX > col * pm.tileSize &&
						obj.world_x + obj.velX < (col + 1) * pm.tileSize &&
						obj.world_y + pm.tileSize + obj.velY > row * pm.tileSize &&
						obj.world_y + obj.velY < (row + 1) * pm.tileSize) {
					return true;
				}
			}	
		}
		return false;
	}
	
	private boolean isCollidingTileFromTop(GameObject obj, int row) {
		return obj.world_y + pm.tileSize < (row + 1) * pm.tileSize && obj.velY >= 0;
	}
	
	private boolean isCollidingTileFromBottom(GameObject obj, int row) {
		return obj.world_y > (row + 1) * pm.tileSize && obj.velY <= 0;
	}
	
	private boolean isCollidingTileFromLeft(GameObject obj, int col, int row) {
		return obj.world_y + pm.tileSize - 1 > row * pm.tileSize && obj.world_x < col * pm.tileSize && obj.velX >= 0;
	}
	
	private boolean isCollidingTileFromRight(GameObject obj, int col, int row) {
		return obj.world_y + pm.tileSize - 1 > row * pm.tileSize && obj.world_x + pm.tileSize - 9 > (col + 1) * pm.tileSize && obj.velX <= 0;
	}

}
