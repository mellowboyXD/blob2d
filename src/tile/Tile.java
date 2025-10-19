package tile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Tile {
    public static int GRASS = 0;
    public static int WALL = 1;
    public static int WATER = 2;
    public static int EARTH = 3;
    public static int TREE = 4;
    public static int SAND = 5;

    public BufferedImage image;
    public boolean collision;

    public Tile(String filePath, boolean collision) {
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(filePath)));
            this.collision = collision;
        } catch(IOException e) {
            //noinspection CallToPrintStackTrace
            e.printStackTrace();
        }
    }

    public Tile(String fp) {
        this(fp, false);
    }
}
