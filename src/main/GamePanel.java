package main;

import entity.Player;
import objects.OBJ;
import tile.TileManager;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Objects;

public class GamePanel extends JPanel implements Runnable {
    public volatile GameState gameState;
    public volatile boolean gameWon;
    public boolean debug = false;
    String notifyText;
    int frameCounter;

    public Timer timer = new Timer();

    public final int originalTileSize = 16;
    public final int scaleFactor = 3;

    public final int TARGET_FPS = 60;

    public final int tileSize = originalTileSize * scaleFactor;
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = maxScreenCol * tileSize;
    public final int screenHeight = maxScreenRow * tileSize;

    KeyHandler keyHandler = new KeyHandler(this);
    Thread gameThread;

    public TileManager tileManager = new TileManager(this);
    public CollisionDetection collisionDetect = new CollisionDetection(this);
    public AssetSetter assetSetter = new AssetSetter(this);

    public FpsCounter fpsCounter = new FpsCounter();

    public UI ui = new UI(this);

    // Set player's initial position and speed
    public Player player = new Player(this, keyHandler);

    // Objects
    public OBJ[] obj;

    // World Settings
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;

    public GamePanel() {
        setPreferredSize(new Dimension(screenWidth, screenHeight));
        setBackground(Color.black);
        setDoubleBuffered(true);

        addKeyListener(keyHandler);
        setFocusable(true);

        gameState = GameState.TITLE_SCREEN;
    }

    public void setup() {
        obj = new OBJ[20];
        assetSetter.setObject();
        player.setDefault();
        gameWon = false;
        notifyText = null;
        frameCounter = 0;
        timer = new Timer();
        timer.start();
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void stopGameThread() {
        gameState = GameState.GAME_OVER;
    }

    public void notify(String text) {
        frameCounter = 0;
        notifyText = text;
    }

    public void winGame() {
        frameCounter = 0;
        gameWon = true;
        stopGameThread();
    }

    @Override
    public void run() {
        double drawInterval = 1_000_000_000.0 / TARGET_FPS;
        double deltaTime = 0.0;
        long lastTime = System.nanoTime();
        long currentTime;

        while(gameThread != null) {
            currentTime = System.nanoTime();

            deltaTime += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if (deltaTime >= 1) {
                update();
                repaint();

                --deltaTime;
                ++frameCounter;
                fpsCounter.update();
            }

        }
    }

    public void update() {
        if (Objects.requireNonNull(gameState) == GameState.PLAY) {
            timer.resume();
            player.update();
        } else {
            timer.pause();
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        if (gameState != GameState.TITLE_SCREEN) {

            // Draw tiles
            tileManager.draw(g2);

            // Draw Objects
            for (OBJ o : obj) {
                if (o != null)
                    o.draw(g2, this);
            }

            player.draw(g2);
        }

        ui.draw(g2);
        g2.dispose();
    }
}
