package main;

import java.awt.Graphics2D;

import objects.Enemy;
import objects.Items;
import objects.Mario;
import objects.TileManager;

public class PlayManager {
	
	public boolean gameOver, gameComplete;
	// Constants
	public int tileSize, maxScreenCol, maxScreenRow, maxWorldCol, maxWorldRow;
	// Objects
	public Mario mario;
	TileManager tManager;
	public CollisionManager cManager;
	public AssetSetter aSetter;
	Sound sound = new Sound();
	public Items items[];
	public Enemy enemies[];
	
	
	public PlayManager(int tileSize, int maxScreenCol, int maxScreenRow, int maxWorldCol, int maxWorldRow) {
		this.tileSize = tileSize;
		this.maxScreenCol = maxScreenCol;
		this.maxScreenRow = maxScreenRow;
		this.maxWorldCol = maxWorldCol;
		this.maxWorldRow = maxWorldRow;
		mario = new Mario(this);
		tManager = new TileManager(this);
		cManager = new CollisionManager(this);
		aSetter = new AssetSetter(this);
		items = new Items[4];
		enemies = new Enemy[17];
	}
	
	private void handleInput() {
		if(KeyHandler.upPressed) {
			mario.jumpRequest();
		}else if(KeyHandler.downPressed) {
			 mario.crouchRequest();
		}else if(KeyHandler.leftPressed) {
			mario.walkLeftRequest();
		}else if(KeyHandler.rightPressed) {
			mario.walkRightRequest();
		}else if(KeyHandler.extLeftPressed) {
			mario.runLeftRequest();
		}else if(KeyHandler.extRightPressed) {
			mario.runRightRequest();
		}else {
			mario.stop();
		}
	}
	
	public void setUpItems() {
		aSetter.setItems();
		aSetter.setEnemies();
		playMusic(0);
	}
	
	public void update() {
		if(mario.dead || mario.complete) {
			stopMusic();
		}
		if(!mario.dead) {
			handleInput();
			for(int i = 0; i < enemies.length; i++) {
				if(enemies[i] != null) {
					enemies[i].update();
					if(enemies[i].dead) {
						enemies[i] = null;
					}
				}
			}
			for(int i = 0; i < items.length; i++) {
				if(items[i] != null) {
					cManager.checkItemCollision(mario, items[i]);
					items[i].update();
				}
			}
		}
		mario.update();
	}
	
	public void draw(Graphics2D g2) {
		tManager.draw(g2);
		for(int i = 0; i < enemies.length; i++) {
			if(enemies[i] != null) {
				enemies[i].draw(g2,  this);
			}
		}
		for(int i = 0; i < items.length; i++) {
			if(items[i] != null) {
				items[i].draw(g2,  this);
			}
		}
		mario.draw(g2);
	}
	
	public void playMusic(int i) {
		sound.setMusicFile(i);
		sound.playMusic();
		sound.loop(); // this is for background
	}
	
	public void stopMusic() {
		sound.stopMusic();
	}
	
	public void playSE(int i) {
		sound.setSEFile(i);
		sound.playSE();
	}
}
