package objects;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.Rectangle;

public class OBJ_Door extends OBJ {
    GamePanel gp;
    public OBJ_Door(GamePanel gp, String imageFilePath, int x, int y) {
        this.gp = gp;
        name = "Door";
        collision = true;
        try {
            image = ImageIO.read(java.util.Objects.requireNonNull(getClass().getResourceAsStream(imageFilePath)));
        } catch(Exception e) {
            //noinspection CallToPrintStackTrace
            e.printStackTrace();
        }
        setPos(x * gp.tileSize, y * gp.tileSize);
        setDefault(gp);
    }

    public void setDefault(GamePanel gp) {
        collisionArea = new Rectangle(worldX, worldY, gp.tileSize, gp.tileSize);
    }
}
