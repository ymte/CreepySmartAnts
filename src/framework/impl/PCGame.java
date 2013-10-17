package framework.impl;


import java.awt.Canvas;
import java.awt.Frame;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import framework.Camera;
import framework.FileIO;
import framework.Game;
import framework.Graphics;
import framework.Input;
import framework.Screen;


public abstract class PCGame implements Game {
	Canvas canvas;
	JFrame frame;
    PCFastRenderView renderView;
    Graphics graphics;
   //Audio audio;
    Input input;
    FileIO fileIO;
    Screen screen;
    public Camera cam;
        
    public PCGame(String title){
    	frame = new JFrame(title);
    	frame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
    	frame.setExtendedState(Frame.MAXIMIZED_BOTH);
    	frame.setUndecorated(true);
    	canvas = new Canvas();
    	frame.add(canvas);
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	frame.setVisible(true);
    	
    	int frameBufferWidth = 1024;
        int frameBufferHeight = 800;
        Image frameBuffer = new BufferedImage(frameBufferWidth, frameBufferHeight, BufferedImage.TYPE_USHORT_565_RGB);
        
        float scaleX = (float) frameBufferWidth
                / frame.getWidth();
        float scaleY = (float) frameBufferHeight
                / frame.getHeight();

        
        double camH = frameBufferWidth/2d/Math.tan(Math.PI/8d);
        cam = new Camera(frameBufferWidth/2d, frameBufferHeight/2d, camH, Math.PI/4, 2d*Math.atan(frameBufferHeight/2d/camH));
        
        renderView = new PCFastRenderView(this, frameBuffer, canvas.getGraphics());
        fileIO = new PCFileIO("/files", "/assets");
        graphics = new PCGraphics(frameBuffer, (PCFileIO)fileIO);
        //audio = new AndroidAudio(this);
        input = new PCInput(canvas, scaleX, scaleY);
        screen = getStartScreen();
        renderView.resume();
    }
    
    public Input getInput() {
        return input;
    }

    public FileIO getFileIO() {
        return fileIO;
    }

    public Graphics getGraphics() {
        return graphics;
    }

    public void setScreen(Screen screen) {
        if (screen == null)
            throw new IllegalArgumentException("Screen must not be null");

        this.screen.pause();
        this.screen.dispose();
        screen.resume();
        screen.update(0);
        this.screen = screen;
        //System.out.println("setScreen = GCVisualiserScreen: " + (this.screen instanceof MallVisualiser.GrandChallenge.GCVisualiserScreen));
    }

    public Screen getCurrentScreen() {
        return screen;
    }
    
    public Camera getCamera(){
    	return cam;
    }
    
    public Frame getContainer(){
    	return frame;
    }
}
