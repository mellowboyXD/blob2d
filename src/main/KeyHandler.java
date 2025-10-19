package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Objects;

public class KeyHandler implements KeyListener {
    private final GamePanel gp;
    public boolean upPressed, downPressed, leftPressed, rightPressed;

    public KeyHandler(GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if(code == KeyEvent.VK_F12) {
           gp.debug = !gp.debug;
        }

        switch (gp.gameState) {
            case TITLE_SCREEN -> {
                switch (code) {
                    case KeyEvent.VK_UP -> {
                        if(--UI.titleMenuIdx < 0) UI.titleMenuIdx = (short) (UI.titleMenuOptions.length - 1);
                    }
                    case KeyEvent.VK_DOWN -> UI.titleMenuIdx = (short) ((++UI.titleMenuIdx) % UI.titleMenuOptions.length);
                    case KeyEvent.VK_ENTER -> {
                        if(Objects.equals(UI.titleMenuOptions[UI.titleMenuIdx], "New Game")) {
                            gp.gameState = GameState.PLAY;
                            gp.setup();
                        } else if (Objects.equals(UI.titleMenuOptions[UI.titleMenuIdx], "Quit")) {
                            System.exit(0);
                        }
                    }
                }
            }
            case PLAY -> {
                switch (code) {
                    case KeyEvent.VK_W, KeyEvent.VK_UP -> upPressed = true;

                    case KeyEvent.VK_A, KeyEvent.VK_LEFT -> leftPressed = true;

                    case KeyEvent.VK_S, KeyEvent.VK_DOWN -> downPressed = true;

                    case KeyEvent.VK_D, KeyEvent.VK_RIGHT -> rightPressed = true;

                    case KeyEvent.VK_ESCAPE -> gp.gameState = GameState.PAUSE;
                }
            }
            case GAME_OVER -> {
                switch (code) {
                    case KeyEvent.VK_UP -> {
                        if(--UI.gameOverMenuIdx < 0) UI.gameOverMenuIdx = (short) (UI.gameOverMenuOptions.length - 1);
                    }

                    case KeyEvent.VK_DOWN -> UI.gameOverMenuIdx = (short) ((++UI.gameOverMenuIdx) % UI.gameOverMenuOptions.length);

                    case KeyEvent.VK_ENTER -> {
                        if (Objects.equals(UI.gameOverMenuOptions[UI.gameOverMenuIdx], "Restart")) {
                            gp.gameState = GameState.PLAY;
                            gp.setup();
                        } else if (Objects.equals(UI.gameOverMenuOptions[UI.gameOverMenuIdx], "Main Menu")) {
                            gp.gameState = GameState.TITLE_SCREEN;
                        }
                    }
                }
            }
            case PAUSE -> {
                if (code == KeyEvent.VK_ESCAPE) {
                    gp.gameState = GameState.PLAY;
                }
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        switch (code) {
            case KeyEvent.VK_W, KeyEvent.VK_UP -> upPressed = false;

            case KeyEvent.VK_A, KeyEvent.VK_LEFT -> leftPressed = false;

            case KeyEvent.VK_S, KeyEvent.VK_DOWN -> downPressed = false;

            case KeyEvent.VK_D, KeyEvent.VK_RIGHT -> rightPressed = false;
        }
    }
}
