package main;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable{
	private static final long serialVersionUID = 1L;
	// Screen Settings
	final int originalTileSize = 16; // 16 x 16 tile
	final int scale = 3;
	
	public final int tileSize = originalTileSize * scale;
	final int maxScreenCol = 16, maxScreenRow = 16;
	final int screenWidth = tileSize * maxScreenCol, screenHeight = tileSize * maxScreenRow; // 768 X 576
	final int FPS = 60;
	
	// World Settings
	public final int maxWorldCol = 224, maxWorldRow = 16;
	public final int worldWidth = tileSize * maxWorldCol, worldHeight = tileSize * maxWorldRow;
	
	Thread gameThread;
	PlayManager pm;
	
	public GamePanel() {
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setDoubleBuffered(true);
		this.addKeyListener(new KeyHandler());
		this.setFocusable(true);
		
		pm = new PlayManager(tileSize, maxScreenCol, maxScreenRow, maxWorldCol, maxWorldRow);
	}
	
	public void launchGame() {
		gameThread = new Thread(this);
		gameThread.start();
	}

	@Override
	public void run() {
		double drawInterval = 1000000000/FPS;
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		
		while(gameThread != null) {
			currentTime = System.nanoTime();
			
			delta += (currentTime - lastTime) / drawInterval;
			lastTime = currentTime;
			
			if(delta >= 1) {
				update();
				repaint();
				delta--;
			}
		}
	}
	
	private void update() {
		pm.update();
	}
	
	// for checking FPS
	long lastTime = System.currentTimeMillis();
	int frames = 0;
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D)g;
		pm.draw(g2);
		
		// checking FPS
		frames++;
	    long currentTime = System.currentTimeMillis();
	    if (currentTime - lastTime >= 1000) {
	        System.out.println("FPS: " + frames);
	        frames = 0;
	        lastTime = currentTime;
	    }
	}
}
