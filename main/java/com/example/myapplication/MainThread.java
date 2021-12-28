package com.example.myapplication;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

/**
 * Created by asdfqwer on 7/15/2017.
 */

public class MainThread extends Thread {
    public static final int MAX_FPS = 30;
    private final SurfaceHolder surfaceHolder;
    private double averageFPS;
    private GamePanel gamePanel;
    private boolean running;
    private boolean pause;
    public static Canvas canvas;

    public void setRunning(boolean running) {
        this.running = running;
    }
    public void Pause(boolean pause) {
        this.pause = pause;
    }

    public MainThread(SurfaceHolder surfaceHolder, GamePanel gamePanel) {
        super();
        this.surfaceHolder = surfaceHolder;
        this.gamePanel = gamePanel;
    }

    @Override
    public void run() {
        long startTime;
        long timeMillis = 1000 / MAX_FPS;
        long waitTime;
        int frameCount = 0;
        long totalTime = 0;
        long targetTime = 1000 / MAX_FPS;

        while (running) {
            while(!pause) {
                startTime = System.nanoTime();
                canvas = null;

                try {
                    canvas = this.surfaceHolder.lockCanvas();
                    synchronized (surfaceHolder) {
                        this.gamePanel.update();
                        this.gamePanel.draw(canvas);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (canvas != null) {
                        try {
                            surfaceHolder.unlockCanvasAndPost(canvas);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                timeMillis = (System.nanoTime() - startTime) / 1000000;
                waitTime = targetTime - timeMillis;
                try {
                    if (waitTime > 0) {
                        this.sleep(waitTime);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                totalTime += System.nanoTime() - startTime;
                frameCount++;
                if (frameCount == 30) {
                    averageFPS = 1000 / ((totalTime / frameCount) / 1000000);
                    frameCount = 0;
                    totalTime = 0;
                }
            }
        }
    }
}