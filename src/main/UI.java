package main;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class UI {
    GamePanel gp;
    Graphics2D g2;

    public static short titleMenuIdx = 0;
    public static String[] titleMenuOptions = {"New Game", "Quit"};
    public static short gameOverMenuIdx = 0;
    public static String[] gameOverMenuOptions = {"Restart", "Main Menu"};

    Font smallFont = new Font("Monospace", Font.PLAIN, 24);
    Font font = new Font("Monospace", Font.PLAIN, 32);
    Font titleFont = new Font("Algerian", Font.BOLD, 72);
    Font debugFont = new Font("Arial", Font.PLAIN, 18);

    BufferedImage image;

    public UI(GamePanel gp) {
        this.gp = gp;
    }

    public int getCenterX(String text, Font f, int x) {
        int w = g2.getFontMetrics(f).stringWidth(text);
        return x - w / 2;
    }

    public void draw(Graphics2D g2) {
        this.g2 = g2;

        if(gp.debug)
            drawDebugInfo();

        switch (gp.gameState) {
            case TITLE_SCREEN -> drawTitleScreen();
            case PLAY -> drawPlayScreen();
            case PAUSE -> drawPauseScreen();
            case GAME_OVER -> drawGameOverScreen();
        }
    }

    private void drawTitleScreen() {
        g2.setFont(titleFont);
        g2.setColor(new Color(150, 75, 0));

        String text = "Blob 2D";
        g2.drawString(text, getCenterX(text, titleFont, gp.screenWidth / 2), gp.screenHeight / 2);

        g2.setFont(font);
        g2.setColor(Color.white);

        int y = gp.screenHeight - 160;
        for (int i = 0; i< titleMenuOptions.length; ++i) {
            text = titleMenuOptions[i];
            if (titleMenuIdx == i)
                text = "> " + text + " <";
            g2.drawString(text, getCenterX(text, font, gp.screenWidth / 2), y);
            y += 36;
        }
    }

    private void drawPlayScreen() {
        drawWithImage(g2, "/objects/key.png", "x " + gp.player.keyCount, 16, 24);

        g2.drawString("Time: " + gp.timer.getElapsedTimeString(), gp.screenWidth - 250, 32);

        if(gp.notifyText != null) {
            g2.drawString(gp.notifyText, 16, 160);
            if (gp.frameCounter >= 120) {
                gp.notifyText = null;
                gp.frameCounter = 0;
            }
        }
    }

    private void drawPauseScreen() {
        String text = "Paused";
        g2.setFont(font);
        g2.setColor(Color.white);
        g2.drawString(text, getCenterX(text, font, gp.screenWidth / 2), gp.screenHeight / 2);

        g2.setFont(smallFont);
        g2.setColor(Color.white);
        text = "Press <Esc> to Continue";
        g2.drawString(text, getCenterX(text, smallFont, gp.screenWidth / 2), gp.screenHeight / 2 + 32);
    }

    private void drawGameOverScreen() {
        String text;
        g2.setFont(titleFont);
        if (gp.gameWon) {
            text = "You won!";
            g2.setColor(Color.yellow);
        } else {
           text = "Game Over";
            g2.setColor(Color.red);
        }
        g2.drawString(text, getCenterX(text, titleFont, gp.screenWidth / 2), gp.screenHeight / 2);

        g2.setFont(font);
        g2.setColor(Color.white);
        text = "Time: " + gp.timer.getElapsedTimeString();
        g2.drawString(text, getCenterX(text, font, gp.screenWidth / 2), gp.screenHeight / 2 + 32);

        g2.setFont(smallFont);
        g2.setColor(Color.white);
        int y = gp.screenHeight / 2 + 64;
        for (int i = 0; i< gameOverMenuOptions.length; ++i) {
            text = gameOverMenuOptions[i];
            if (gameOverMenuIdx == i)
                text = "> " + text + " <";
            g2.drawString(text, getCenterX(text, smallFont, gp.screenWidth / 2), y);
            y += 32;
        }
    }

    private void drawDebugInfo() {
        g2.setFont(debugFont);
        g2.setColor(Color.white);
        g2.drawString("FPS: " + gp.fpsCounter.getFps(), 16, gp.screenHeight - 16);
    }

    public void setImage(String filePath) {
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(filePath)));
        } catch (IOException e) {
            //noinspection CallToPrintStackTrace
            e.printStackTrace();
        }
    }

    private void drawWithImage(Graphics2D g2, String imageFilePath, String text, int x, int y) {
        setImage(imageFilePath);
        g2.drawImage(image, x, y - image.getHeight(), gp.tileSize, gp.tileSize, null);
        g2.setFont(font);
        g2.setColor(Color.white);
        g2.drawString(text, x + gp.tileSize, y + image.getHeight());
    }
}
