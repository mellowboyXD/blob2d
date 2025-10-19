package entity;

import main.GamePanel;
import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Player extends Entity{
    GamePanel gp;
    KeyHandler keyH;

    final int FRAME_DELAY = 6;
    public final int screenX;
    public final int screenY;
    int frameCounter = 0;
    boolean isMoving = false;
    int pixelCounter = 0;
    public int keyCount = 0;

    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;
        screenX = this.gp.screenWidth / 2 - (this.gp.tileSize / 2);
        screenY = this.gp.screenHeight / 2 - (this.gp.tileSize / 2);
        setDefault();
    }

    @Override
    public int getScreenX() {
        return screenX;
    }

    @Override
    public int getScreenY() {
        return screenY;
    }

    @Override
    public void setDefault() {
        worldX = gp.tileSize * 25;
        worldY = gp.tileSize * 24;
        isMoving = false;
        speed = 8;
        keyCount = 0;
        direction = "up";
        pixelCounter = 0;
        collisionArea = new Rectangle(screenX + 2, screenY + 1, gp.tileSize - 2, gp.tileSize - 3);
        getImage();
    }

    public void getImage() {
        try {
            up1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/up_0.png")));
            up2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/up_1.png")));
            down1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/down_0.png")));
            down2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/down_1.png")));
            right1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/right_0.png")));
            right2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/right_1.png")));
            left1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/left_0.png")));
            left2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/left_1.png")));
        } catch (IOException e) {
            //noinspection CallToPrintStackTrace
            e.printStackTrace();
        }
    }

    public void collideWithObject(int i) {
        if (i == -1)
            return;
        switch (gp.obj[i].name) {
            case "Key" -> {
                ++keyCount;
                gp.notify("Picked up key!");
                gp.obj[i] = null;
            }
            case "Banana" -> {
                speed += 4;
                gp.obj[i] = null;
                gp.notify("Ate Banana! Speed + 4");
            }
            case "Banana Peel" -> {
                speed = 4;
                gp.notify("Ouch! Stepped on Banana Peel");
            }
            case "Door" -> {
                if(keyCount >= 1) {
                    --keyCount;
                    collisionOn = false;
                    gp.obj[i] = null;
                } else {
                    gp.notify("Door is locked! Needs a key!");
                    collisionOn = true;
                }
            }
            case "Chest" -> {
                gp.winGame();
            }
        }
    }

    @Override
    public void update() {
        if (!isMoving) {
            if (keyH.upPressed || keyH.rightPressed || keyH.downPressed || keyH.leftPressed) {
                if (keyH.upPressed) {
                    direction = "up";
                } else if (keyH.downPressed) {
                    direction = "down";
                } else if (keyH.leftPressed) {
                    direction = "left";
                } else {
                    direction = "right";
                }

                isMoving = true;

                collisionOn = false;
                gp.collisionDetect.checkTile(this);
                int objIdx = gp.collisionDetect.checkObj(gp.obj, this);
                collideWithObject(objIdx);
            } else {
                ++frameCounter;
                if (frameCounter == FRAME_DELAY) {
                    spriteNum = 1;
                    frameCounter = 0;
                }
            }
        }

        if (isMoving) {
            if (!collisionOn) {
                switch (direction) {
                    case "up" -> worldY -= speed;
                    case "right" -> worldX += speed;
                    case "down" -> worldY += speed;
                    case "left" -> worldX -= speed;
                }
            }

            spriteCounter++;
            if (spriteCounter >= FRAME_DELAY) {
                if (spriteNum == 1)
                    spriteNum = 2;
                else if (spriteNum == 2)
                    spriteNum = 1;
                spriteCounter = 0;
            }
            pixelCounter += speed;

            if (pixelCounter >= gp.tileSize) {
                isMoving = false;
                pixelCounter = 0;
            }
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        BufferedImage image = null;

        switch (direction) {
            case "up" -> {
                image = (spriteNum == 1) ? up1 : up2;
            }
            case "right" -> {
                image = (spriteNum == 1) ? right1 : right2;
            }
            case "down" -> {
                image = (spriteNum == 1) ? down1 : down2;
            }
            case "left" -> {
                image = (spriteNum == 1) ? left1 : left2;
            }
        }

        g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);

        if (gp.debug) {
            g2.setColor(Color.blue);
            g2.draw(collisionArea);
        }
    }
}
