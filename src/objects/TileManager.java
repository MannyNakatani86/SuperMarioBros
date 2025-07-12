package objects;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import main.PlayManager;

public class TileManager {

	PlayManager pm;
	public Tile[] tile;
	public int mapTileNum[][];
	
	public TileManager(PlayManager pm) {
		this.pm = pm;
		tile = new Tile[20]; // number of tile types
		mapTileNum = new int[pm.maxWorldCol][pm.maxWorldRow];
		getTileImage();
		loadMap("/tile/Stage1.1.csv");
	}
	
	public void getTileImage() {
		try {
			tile[0] = new Tile();
			tile[0].image = ImageIO.read(getClass().getResourceAsStream("/tile/sky.png"));
			
			tile[1] = new Tile();
			tile[1].image = ImageIO.read(getClass().getResourceAsStream("/tile/ground_block.png"));
			tile[1].collision = true;
			
			tile[2] = new Tile();
			tile[2].image = ImageIO.read(getClass().getResourceAsStream("/tile/normal_block.png"));
			tile[2].collision = true;
			
			tile[3] = new Tile();
			tile[3].image = ImageIO.read(getClass().getResourceAsStream("/tile/question_block.png"));
			tile[3].collision = true;
			
			tile[4] = new Tile();
			tile[4].image = ImageIO.read(getClass().getResourceAsStream("/tile/stair_block.png"));
			tile[4].collision = true;
			
			tile[5] = new Tile();
			tile[5].image = ImageIO.read(getClass().getResourceAsStream("/tile/pipeTopL.png"));
			tile[5].collision = true;
			
			tile[6] = new Tile();
			tile[6].image = ImageIO.read(getClass().getResourceAsStream("/tile/pipeTopR.png"));
			tile[6].collision = true;
			
			tile[7] = new Tile();
			tile[7].image = ImageIO.read(getClass().getResourceAsStream("/tile/pipeL.png"));
			tile[7].collision = true;
			
			tile[8] = new Tile();
			tile[8].image = ImageIO.read(getClass().getResourceAsStream("/tile/pipeR.png"));
			tile[8].collision = true;
			
			tile[9] = new Tile();
			tile[9].image = ImageIO.read(getClass().getResourceAsStream("/tile/bushL.png"));
			
			tile[10] = new Tile();
			tile[10].image = ImageIO.read(getClass().getResourceAsStream("/tile/bushM.png"));
			
			tile[11] = new Tile();
			tile[11].image = ImageIO.read(getClass().getResourceAsStream("/tile/bushR.png"));
			
			tile[11] = new Tile();
			tile[11].image = ImageIO.read(getClass().getResourceAsStream("/tile/bushR.png"));
			
			tile[12] = new Tile();
			tile[12].image = ImageIO.read(getClass().getResourceAsStream("/tile/mountain1.png"));
			
			tile[13] = new Tile();
			tile[13].image = ImageIO.read(getClass().getResourceAsStream("/tile/mountain3.png"));
			
			tile[14] = new Tile();
			tile[14].image = ImageIO.read(getClass().getResourceAsStream("/tile/mountain2.png"));
			
			tile[15] = new Tile();
			tile[15].image = ImageIO.read(getClass().getResourceAsStream("/tile/mountain_dot.png"));
			
			tile[16] = new Tile();
			tile[16].image = ImageIO.read(getClass().getResourceAsStream("/tile/mountain_dot2.png"));
			
			tile[17] = new Tile();
			tile[17].image = ImageIO.read(getClass().getResourceAsStream("/tile/mountain_gr.png"));
			
			tile[18] = new Tile();
			tile[18].image = ImageIO.read(getClass().getResourceAsStream("/tile/empty_block.png"));
			tile[18].collision = true;
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void loadMap(String map) {
		try {
			InputStream is = getClass().getResourceAsStream(map);
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			
			int col = 0, row = 0;
			while(col < pm.maxWorldCol && row < pm.maxWorldRow) {
				String line = br.readLine();
				while(col < pm.maxWorldCol) {
					String numbers[] = line.split(",");
					int num = Integer.parseInt(numbers[col]);
					mapTileNum[col][row] = num;
					col++;
				}
				if(col == pm.maxWorldCol) {
					col = 0;
					row++;
				}
			}
			br.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	public void draw(Graphics2D g2) {
		int worldCol = 0, worldRow = 0;
		while(worldCol < pm.maxWorldCol && worldRow < pm.maxWorldRow) {
			int tileNum = mapTileNum[worldCol][worldRow];
			int world_x = worldCol * pm.tileSize;
			int world_y = worldRow * pm.tileSize;
			int screen_x = world_x - pm.mario.world_x + pm.mario.screen_x;
			int screen_y = world_y;

			if(world_x > pm.mario.world_x - pm.mario.screen_x - pm.tileSize && world_x < pm.mario.world_x + pm.mario.screen_x + pm.tileSize * 10 ) {
				if(worldCol == 64 && worldRow == 9) { // this is for that one invisible block
					if(mapTileNum[worldCol][worldRow] == 3) {
						g2.drawImage(tile[0].image,  screen_x,  screen_y,  pm.tileSize, pm.tileSize, null);
					}else {
						g2.drawImage(tile[18].image,  screen_x,  screen_y,  pm.tileSize, pm.tileSize, null);
					}
				}else {
					g2.drawImage(tile[tileNum].image,  screen_x,  screen_y,  pm.tileSize, pm.tileSize, null);
				}
			}
			
			worldCol++;
			if(worldCol == pm.maxWorldCol) {
				worldCol = 0;
				worldRow++;
			}
		}
		
	
	}
}
