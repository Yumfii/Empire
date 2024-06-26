package tile;

import java.awt.Graphics2D;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

public class TileManager {

	GamePanel gp;
	// Array for images
	public Tile[] tile;
	// 2 Dimensional array to read the map
	public int mapTileNum[][];

	public TileManager(GamePanel gp) {

		this.gp = gp;

		// Array sets
		tile = new Tile[71];
		mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];

		// Sets images for places in the array
		getTileImage();
		// Loads the map file
		loadMap("/maps/map1.txt");
	}

	public void getTileImage() {

		setup(0, "Grass0", false);
		setup(7, "Tree0", true);
		setup(10, "Tree0", true);
		setup(41, "Tree0", true);
		setup(48, "Tree0", true);
		setup(49, "Tree0", true);
		setup(50, "Tree0", true);
		setup(53, "Tree0", true);
		setup(70, "Tree0", true);

	}

	// THE CONNECTION OF ARRAY INDEXES WITH THE IMAGES
	public void setup(int index, String imageName, boolean collision) {

		// SCALE FOR IMAGES
		UtilityTool uTool = new UtilityTool();

		try {

			tile[index] = new Tile();
			tile[index].image = ImageIO.read(getClass().getResourceAsStream("/tiles/" + imageName + ".png"));
			tile[index].image = uTool.scaleImage(tile[index].image, gp.tileSize, gp.tileSize);
			tile[index].collision = collision;

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// LOADS ALL THE IMAGES TO THEIR NUMBERS
	public void loadMap(String filePath) {

		try {

			InputStream is = getClass().getResourceAsStream(filePath);
			BufferedReader br = new BufferedReader(new InputStreamReader(is));

			int col = 0;
			int row = 0;

			while (col < gp.maxWorldCol && row < gp.maxWorldRow) {

				String line = br.readLine();

				while (col < gp.maxWorldCol) {

					String numbers[] = line.split(" ");

					int num = Integer.parseInt(numbers[col]);

					mapTileNum[col][row] = num;
					col++;
				}
				if (col == gp.maxWorldCol) {
					col = 0;
					row++;
				}
			}
			br.close();

		} catch (Exception e) {

		}
	}

	// DRAWS ALL THE IMAGES ON THE SCREEN (WITH A DISTANCE FROM EACH OTHER)
	public void draw(Graphics2D g2) {

		int worldCol = 0;
		int worldRow = 0;

		while (worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {

			int tileNum = mapTileNum[worldCol][worldRow];

			int worldX = worldCol * gp.tileSize;
			int worldY = worldRow * gp.tileSize;
			int screenX = worldX - gp.player.worldX + gp.player.screenX;
			int screenY = worldY - gp.player.worldY + gp.player.screenY;

			if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
					worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
					worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
					worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {

				g2.drawImage(tile[tileNum].image, screenX, screenY, null);
			}

			worldCol++;

			if (worldCol == gp.maxWorldCol) {
				worldCol = 0;
				worldRow++;
			}
		}

	}
}
