package framework.impl;

import java.awt.Graphics;
import java.awt.Image;

import framework.Screen;

public class PCFastRenderView implements Runnable {
    PCGame game;
    Graphics g;
    Image framebuffer;
    Thread renderThread = null;
    volatile boolean running = false;
    private int frameWidth;
    private int frameHeight;

    public PCFastRenderView(PCGame game, Image frameBuffer, Graphics g) {
        this.game = game;
        this.framebuffer = frameBuffer;
        this.g = g;
        frameWidth = game.frame.getWidth();
        frameHeight = game.frame.getHeight();
    }

    public void resume() { 
        running = true;
        renderThread = new Thread(this);
        renderThread.start();         
    }      

    public void run() {
        long startTime = System.nanoTime();
        Screen screen;
        while(running) {           
            
            float deltaTime = (System.nanoTime()-startTime) / 1000000000.0f;
            startTime = System.nanoTime();
            
            screen = game.getCurrentScreen();;
            screen.update(deltaTime);
            screen.present(deltaTime);
            
            g.drawImage(framebuffer, 0, 0, frameWidth, frameHeight, null);
        }
    }

    public void pause() {                        
        running = false;                        
        while(true) {
            try {
                renderThread.join();
                return;
            } catch (InterruptedException e) {
                // retry
            }
        }
    }        
}
