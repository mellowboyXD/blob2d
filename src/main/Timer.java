package main;

public class Timer {
    private long pausedTime;
    private long totalPauseTime;
    private boolean isPaused;
    private long startTime;

    public Timer() {
        totalPauseTime = 0;
        isPaused = false;
    }

    public void start() {
        startTime = System.currentTimeMillis();
    }

    public void pause() {
        if(!isPaused) {
            pausedTime = System.currentTimeMillis();
            isPaused = true;
        }
    }

    public void resume() {
        if(isPaused) {
            totalPauseTime += System.currentTimeMillis() - pausedTime;
            isPaused = false;
        }
    }

    public long getElapsedTimeMillis() {
        if(isPaused) {
            return pausedTime - startTime - totalPauseTime;
        }
        return System.currentTimeMillis() - startTime - totalPauseTime;
    }

    public String getElapsedTimeString() {
        long time = getElapsedTimeMillis();
        int millis = (int)time % 1000;
        int totalSeconds = (int)time / 1000;
        int seconds = totalSeconds % 60;
        int minutes = totalSeconds / 60;

        return String.format("%02d:%02d:%03d", minutes, seconds, millis);
    }
}
