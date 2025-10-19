package objects;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.Rectangle;

public class OBJ_Banana extends OBJ {
    GamePanel gp;
    public OBJ_Banana(GamePanel gp, String imageFilePath, int x, int y) {
        this.gp = gp;
        collision = true;
        getSprite(imageFilePath, "Banana");
        setPos(x * gp.tileSize, y * gp.tileSize);
        setDefault(gp);
    }

    public void getSprite(String imageFilePath, String name) {
        this.name = name;
        try {
            image = ImageIO.read(java.util.Objects.requireNonNull(getClass().getResourceAsStream(imageFilePath)));
        } catch(Exception e) {
            //noinspection CallToPrintStackTrace
            e.printStackTrace();
        }
    }

    public void setDefault(GamePanel gp) {
        collisionArea = new Rectangle(worldX, worldY, gp.tileSize, gp.tileSize);
    }
}
