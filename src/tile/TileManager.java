package tile;

import main.GamePanel;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileManager {
    GamePanel gp;
    public Tile[] tiles;
    public int[][] mapTilesNum;

    public TileManager(GamePanel gp) {
        this.gp = gp;
        tiles = new Tile[10];
        mapTilesNum = new int[this.gp.maxWorldCol][this.gp.maxWorldRow];
        loadMap("/maps/world01.txt");
        getTileImages();
    }

    public void getTileImages() {
        tiles[Tile.GRASS] = new Tile("/tiles/grass.png");
        tiles[Tile.WALL] = new Tile("/tiles/wall.png", true);
        tiles[Tile.WATER] = new Tile("/tiles/water.png", true);
        tiles[Tile.EARTH] = new Tile("/tiles/earth.png");
        tiles[Tile.TREE] = new Tile("/tiles/tree.png", true);
        tiles[Tile.SAND] = new Tile("/tiles/sand.png");
    }

    public void loadMap(String fp) {
        try {
            InputStream is = getClass().getResourceAsStream(fp);
            assert is != null;
            BufferedReader br = new BufferedReader(new InputStreamReader((is)));

            int col = 0;
            int row = 0;

            while(col < gp.maxWorldCol && row < gp.maxWorldRow) {
                String line = br.readLine();
                while(col < gp.maxWorldCol) {
                    String[] numberStr = line.split(" ");
                    int num = Integer.parseInt(numberStr[col]);

                    mapTilesNum[col][row] = num;
                    ++col;
                }

                if(col == gp.maxWorldCol) {
                    col = 0;
                    ++row;
                }
            }
            br.close();
        } catch (Exception e) {
            //noinspection CallToPrintStackTrace
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2) {
        int worldCol = 0;
        int worldRow = 0;

        while(worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {
            int tileNum = mapTilesNum[worldCol][worldRow];

            int worldX = worldCol * gp.tileSize;
            int worldY = worldRow * gp.tileSize;

            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;

            if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                worldY - gp.tileSize < gp.player.worldY + gp.player.screenY){

                g2.drawImage(tiles[tileNum].image, screenX, screenY, gp.tileSize, gp.tileSize, null);
            }
            ++worldCol;

            if(worldCol == gp.maxWorldCol) {
                worldCol = 0;
                ++worldRow;
            }
        }
    }
}
