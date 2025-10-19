package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public abstract class Entity {
    public int worldX, worldY;
    public int speed;

    public BufferedImage up1, up2, right1, right2, down1, down2, left1, left2;
    public String direction;
    public int spriteCounter = 0;
    public int spriteNum = 1;

    public Rectangle collisionArea;
    public boolean collisionOn = false;

    public abstract int getScreenX();
    public abstract int getScreenY();

    public abstract void setDefault();
    public abstract void update();
    public abstract void draw(Graphics2D g2);
}
