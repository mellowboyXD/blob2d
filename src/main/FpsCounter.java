package main;

public class FpsCounter {
    private long oldTime;
    private short frames;
    private short currentFps;

    public FpsCounter() {
        oldTime = System.nanoTime();
        frames = 0;
    }

    public void update() {
        long newTime = System.nanoTime();
        ++frames;
        if (newTime - oldTime >= 1_000_000_000) {
            currentFps = frames;
            frames = 0;
            oldTime = System.nanoTime();
        }
    }

    public int getFps() {
        return currentFps;
    }
}
