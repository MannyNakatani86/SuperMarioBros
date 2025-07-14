package main;

import objects.Enemy;
import objects.GameObject;
import objects.Items;
import objects.Mario;

public class CollisionManager {
	
	private PlayManager pm;

	public CollisionManager(PlayManager pm) {
		this.pm = pm;
	}
	
	// Collision with tiles
	public void checkTileCollision(GameObject obj) {
		if(obj != null) {
			int col = 0, row = 0;
			boolean bottomCollision = false;
			while(col < pm.maxWorldCol && row < pm.maxScreenRow) {
				while(col < pm.maxWorldCol) {
					if(isOverlappingTile(obj, col, row)) {
						if(isCollidingTileFromTop(obj, row)) {
							bottomCollision = true;
							obj.manageLanding(row * pm.tileSize);
						}
						if(isCollidingTileFromBottom(obj, row)) {
							obj.manageUp((row + 1) * pm.tileSize);
							if(pm.tManager.mapTileNum[col][row] == 3) {
								pm.tManager.mapTileNum[col][row] = 18; // if question block, change to empty block
							}
							if(pm.tManager.mapTileNum[col][row] == 2) {
								pm.tManager.mapTileNum[col][row] = 0; // if normal block, change to sky
								pm.playSE(11);
							}
						}else {
							if(isCollidingTileFromLeft(obj, col, row)) {
								if(obj instanceof Mario) {
									if(pm.tManager.mapTileNum[col][row] == 19) {
										obj.manageRight((col - 1) * pm.tileSize + 37);
										((Mario) obj).complete = true;
									}else {
										obj.manageRight((col - 1) * pm.tileSize + 9);
									}
								}else {
									obj.manageRight((col - 1) * pm.tileSize);
								}
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
	}
	
	private boolean isOverlappingTile(GameObject obj, int col, int row) {
		int num = pm.tManager.mapTileNum[col][row];
		if(pm.tManager.tile[num].collision) {
			if(obj instanceof Mario) {
				if(num == 19) {
					if(obj.world_x + pm.tileSize - 37 + obj.velX > col * pm.tileSize &&
							obj.world_x + obj.velX < (col + 1) * pm.tileSize &&
							obj.world_y + obj.height + obj.velY > row * pm.tileSize &&
							obj.world_y + obj.velY < (row + 1) * pm.tileSize) {
						return true;
					}
				}else {
					if(obj.world_x + pm.tileSize - 9 + obj.velX > col * pm.tileSize &&
							obj.world_x + obj.velX < (col + 1) * pm.tileSize &&
							obj.world_y + obj.height + obj.velY > row * pm.tileSize &&
							obj.world_y + obj.velY < (row + 1) * pm.tileSize) {
						return true;
					}
				}
			}else {
				if(obj.world_x + pm.tileSize + obj.velX > col * pm.tileSize &&
						obj.world_x + obj.velX < (col + 1) * pm.tileSize &&
						obj.world_y + obj.height + obj.velY > row * pm.tileSize &&
						obj.world_y + obj.velY < (row + 1) * pm.tileSize) {
					return true;
				}
			}	
		}
		return false;
	}
	
	private boolean isCollidingTileFromTop(GameObject obj, int row) {
		return obj.world_y + obj.height < (row + 1) * pm.tileSize && obj.velY >= 0;
	}
	
	private boolean isCollidingTileFromBottom(GameObject obj, int row) {
		return obj.world_y > (row + 1) * pm.tileSize && obj.velY <= 0;
	}
	
	private boolean isCollidingTileFromLeft(GameObject obj, int col, int row) {
		return obj.world_y + obj.height - 1 > row * pm.tileSize && obj.world_x < col * pm.tileSize && obj.velX >= 0;
	}
	
	private boolean isCollidingTileFromRight(GameObject obj, int col, int row) {
		return obj.world_y + obj.height - 1 > row * pm.tileSize && obj.world_x + pm.tileSize - 9 > (col + 1) * pm.tileSize && obj.velX <= 0;
	}
	
	// Collision with objects
	public void checkEnemyCollision(GameObject obj, Enemy e) {
		if(obj != null && e != null) {
			if(isOverlappingEnemy(obj, e)) {
				if(isCollidingEnemyFromTop(obj, e)) {
					if(obj instanceof Mario) {
						((Mario) obj).littleJump();
						e.gotHit();
					}
				}
				if(isCollidingEnemyFromBottom(obj, e)) {
					
				}else {
					if(isCollidingEnemyFromLeft(obj, e)) {
						if(obj instanceof Mario) {
							((Mario) obj).levelDown();
						}else {
							obj.manageRight(e.world_x - pm.tileSize);
						}
					}
					if(isCollidingEnemyFromRight(obj, e)) {
						if(obj instanceof Mario) {
							((Mario) obj).levelDown();
						}else {
							obj.manageLeft(e.world_x + pm.tileSize);
						}
					}
				}
			}
		}
	}
	
	private boolean isOverlappingEnemy(GameObject obj, Enemy e) {
		if(obj instanceof Mario) {
			if(obj.world_x + pm.tileSize - 9 + obj.velX > e.world_x &&
					obj.world_x + obj.velX < e.world_x + pm.tileSize &&
					obj.world_y + obj.height + obj.velY > e.world_y &&
					obj.world_y + obj.velY < e.world_y + pm.tileSize ) {
				return true;
			}
		}else {
			if(obj != e) {
				if(obj.world_x + pm.tileSize + obj.velX > e.world_x &&
						obj.world_x + obj.velX < e.world_x + pm.tileSize &&
						obj.world_y + obj.height + obj.velY > e.world_y &&
						obj.world_y + obj.velY < e.world_y + pm.tileSize ) {
					return true;
				}
			}
		}
		return false;
	}
	
	private boolean isCollidingEnemyFromTop(GameObject obj, Enemy e) {
		return obj.world_y + obj.height < e.world_y + e.height && obj.velY >= 0;
	}
	
	private boolean isCollidingEnemyFromBottom(GameObject obj, Enemy e) {
		return obj.world_y > e.world_y + e.height && obj.velY <= 0;
	}
	
	private boolean isCollidingEnemyFromLeft(GameObject obj, Enemy e) {
		return obj.world_y + obj.height - 1 > e.world_y && obj.world_x < e.world_x && obj.velX >= 0;
	}
	
	private boolean isCollidingEnemyFromRight(GameObject obj, Enemy e) {
		return obj.world_y + obj.height - 1 > e.world_y && obj.world_x + obj.height - 9 > e.world_x + e.height && obj.velX <= 0;
	}
	
	public void checkGroundCollision(GameObject obj) {
		if(obj != null) {
			int col = 0, row = 0;
			boolean groundCollision = false;
			while(col < pm.maxWorldCol && row < pm.maxScreenRow) {
				while(col < pm.maxWorldCol) {
					if(isOverlappingTile(obj, col, row)) {
						if(isCollidingTileFromTop(obj, row)) {
							if(pm.tManager.mapTileNum[col][row] != 19 && pm.tManager.mapTileNum[col][row] != 20) {
								groundCollision = true;
								obj.manageLanding(row * pm.tileSize);
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
			if(!groundCollision) {
				obj.onFeet = false;
			}
		}
	}
	
	// Collision with items for Mario
	public void checkItemCollision(GameObject obj, Items i) {
		if(obj != null && i != null) {
			if(isOverlappingItem(obj, i)) {
				if(isCollidingItemFromBottom(obj, i)) {
					i.visible = true;
					i.hitCount++;
				}
				if(i.hitCount == 1 && i.canGetHit) {
					i.hitCount++;
				}
			}
		}
	}
	
	private boolean isOverlappingItem(GameObject obj, Items i) {
		if(obj instanceof Mario) {
			if(obj.world_x + pm.tileSize - 9 + obj.velX > i.world_x &&
					obj.world_x + obj.velX < i.world_x + pm.tileSize &&
					obj.world_y + obj.height + obj.velY > i.world_y &&
					obj.world_y + obj.velY < i.world_y + pm.tileSize ) {
				return true;
			}
		}
		return false;
	}

	private boolean isCollidingItemFromBottom(GameObject obj, Items i) {
		return obj.world_y > i.world_y && obj.velY <= 0;
	}
	

}
