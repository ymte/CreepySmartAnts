package framework.impl;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import framework.Camera;
import framework.Graphics;
import framework.Pixmap;


public class PCGraphics implements Graphics {
    Image frameBuffer;
    java.awt.Graphics g;
    int bufferWidth;
    int bufferHeight;
    PCFileIO fileIO;
    Font font;

    public PCGraphics(Image frameBuffer, PCFileIO fileIO) {
    	this.fileIO = fileIO;
        this.frameBuffer = frameBuffer;
        g = this.frameBuffer.getGraphics();
        bufferWidth = frameBuffer.getWidth(null);
        bufferHeight = frameBuffer.getHeight(null);
    }

    public Pixmap newPixmap(String fileName, PixmapFormat format) {
        BufferedImage image = null;
        try {
        	image = ImageIO.read(getClass().getResource(fileIO.assetsPath + fileName));
            if (image == null)
                throw new RuntimeException("Couldn't load bitmap from asset '"
                        + fileName + "'");
        } catch (IOException e) {
            
            throw new RuntimeException("Couldn't load bitmap from asset '"
            		+ fileIO.assetsPath + fileName + "'");
        }
                
        if (image.getType() == BufferedImage.TYPE_USHORT_565_RGB)
            format = PixmapFormat.RGB565;
        else if (image.getType() == BufferedImage.TYPE_INT_ARGB)
            format = PixmapFormat.ARGB8888;
        else
            format = PixmapFormat.ARGB4444;

        return new PCPixmap(image, format);
    }
    
    public Pixmap newAbsolutePixmap(String absoluteFileName, PixmapFormat format) {
        BufferedImage image = null;
        try {
        	image = ImageIO.read(new File(absoluteFileName));
            if (image == null)
                throw new RuntimeException("Couldn't load bitmap from asset '"
                        + absoluteFileName + "'");
        } catch (IOException e) {
            throw new RuntimeException("Couldn't load bitmap from asset '"
            		+ absoluteFileName + "'");
        }
                
        if (image.getType() == BufferedImage.TYPE_USHORT_565_RGB)
            format = PixmapFormat.RGB565;
        else if (image.getType() == BufferedImage.TYPE_INT_ARGB)
            format = PixmapFormat.ARGB8888;
        else
            format = PixmapFormat.ARGB4444;

        return new PCPixmap(image, format);
    }
    
    public Pixmap rotatePixmap(Pixmap pix, double radian){		
    	//TODO: only works for 90*n degree rotations
    	PCPixmap pcpix =((PCPixmap) pix);
		AffineTransform transform = new AffineTransform();
		if(radian == Math.PI)
			transform.translate(pix.getWidth()/2, pix.getHeight()/2);
		else
			transform.translate(pix.getHeight()/2, pix.getWidth()/2);
		transform.rotate(radian);
			transform.translate(-pix.getWidth()/2, -pix.getHeight()/2);
	    AffineTransformOp op = new AffineTransformOp(transform, AffineTransformOp.TYPE_BILINEAR);
	    return new PCPixmap(op.filter(pcpix.bitmap, null),pcpix.format);
    }

    public void clear(int color) {
    	g.setColor(new Color(color));
    	g.fillRect(
    			0,
    			0,
    			bufferWidth,
    			bufferHeight);
    }
    
    public void setFontSize(int size){
    	if(font!=null){
    		font = new Font(Font.SANS_SERIF, Font.PLAIN, size);
    	}else{
    		font = new Font(Font.SANS_SERIF, Font.PLAIN, size);
    	}
    }

    /////////////////////////////////////////
    
    public void drawPixel(double x, double y, int color, Camera cam) {
        g.setColor(new Color(color));
        g.drawRect(
        		(int)((x-cam.viewX)/cam.viewW*bufferWidth),
        		(int)((y-cam.viewY)/cam.viewH*bufferHeight),
        		1,
        		1
        		);
    }

    public void drawLine(double x, double y, double x2, double y2, int color, Camera cam) {
        g.setColor(new Color(color));
        g.drawLine(
        		(int)((x-cam.viewX)/cam.viewW*bufferWidth),
        		(int)((y-cam.viewY)/cam.viewH*bufferHeight),
        		(int)((x2-cam.viewX)/cam.viewW*bufferWidth),
        		(int)((y2-cam.viewY)/cam.viewH*bufferHeight)
        		);
    }

    public void drawRect(double x, double y, double width, double height, int color, Camera cam) {
        g.setColor(new Color(color));
        g.fillRect(
        		(int)((x-cam.viewX)/cam.viewW*bufferWidth),
        		(int)((y-cam.viewY)/cam.viewH*bufferHeight),
        		(int)(width/cam.viewW*bufferWidth),
        		(int)(height/cam.viewH*bufferHeight)
        		);
    }
    
    public void drawRect(double x, double y, double width, double height, int c_r, int c_g, int c_b, int c_alpha, Camera cam) {
        g.setColor(new Color(c_r,c_g,c_b,c_alpha));
        g.fillRect(
        		(int)((x-cam.viewX)/cam.viewW*bufferWidth),
        		(int)((y-cam.viewY)/cam.viewH*bufferHeight),
        		(int)(width/cam.viewW*bufferWidth),
        		(int)(height/cam.viewH*bufferHeight)
        		);
    }
    
    public void drawRectRealSize(double x, double y, double width, double height, int c_r, int c_g, int c_b, int c_alpha, Camera cam) {
        g.setColor(new Color(c_r,c_g,c_b,c_alpha));
        g.fillRect(
        		(int)((x-cam.viewX)/cam.viewW*bufferWidth),
        		(int)((y-cam.viewY)/cam.viewH*bufferHeight),
        		(int)width,
        		(int)height
        		);
    }
    
    public void drawRect2(double x0, double y0, double x1, double y1, int color, Camera cam) {
        g.setColor(new Color(color));
       	
        g.fillRect(
        		(int)((x0-cam.viewX)/cam.viewW*bufferWidth),
        		(int)((y0-cam.viewY)/cam.viewH*bufferHeight),
        		(int)((x1-x0+1)/cam.viewW*bufferWidth),
        		(int)((y1-y0+1)/cam.viewH*bufferHeight)
        		);
    }

    public void drawPixmap(Pixmap pixmap, double x, double y, int srcX, int srcY,
            int srcWidth, int srcHeight, Camera cam) {

        g.drawImage(((PCPixmap) pixmap).bitmap,
        		(int)((x-cam.viewX)/cam.viewW*bufferWidth),//dx1,
        		(int)((y-cam.viewY)/cam.viewH*bufferHeight),//dy1,
        		(int)((x-cam.viewX + srcWidth - 1)/cam.viewW*bufferWidth),//dx2,
        		(int)((y-cam.viewY + srcHeight - 1)/cam.viewH*bufferHeight),//dy2,
        		srcX,//sx1,
        		srcY,//sy1,
        		srcX + srcWidth - 1,//sx2,
        		srcY + srcHeight - 1,//sy2,
        		null);
    }
    
    public void drawPixmap(Pixmap pixmap, double x, double y,
            double dstWidth, double dstHeight, Camera cam){
    	
    	g.drawImage(((PCPixmap) pixmap).bitmap,
    			(int)((x-cam.viewX)/cam.viewW*bufferWidth),
    			(int)((y-cam.viewY)/cam.viewH*bufferHeight),
    			(int)(dstWidth/cam.viewW*bufferWidth),
    			(int)(dstHeight/cam.viewH*bufferHeight),
    			null);
    }
    
    public void drawPixmap(Pixmap pixmap, double x, double y, int srcX, int srcY,
            int srcWidth, int srcHeight, double dstWidth, double dstHeight, Camera cam) {

        g.drawImage(((PCPixmap) pixmap).bitmap,
        		(int)((x-cam.viewX)/cam.viewW*bufferWidth),//dx1,
        		(int)((y-cam.viewY)/cam.viewH*bufferHeight),//dy1,
        		(int)((x-cam.viewX + dstWidth - 1)/cam.viewW*bufferWidth),//dx2,
        		(int)((y-cam.viewY + dstHeight - 1)/cam.viewH*bufferHeight),//dy2,
        		srcX,//sx1,
        		srcY,//sy1,
        		srcX + srcWidth - 1,//sx2,
        		srcY + srcHeight - 1,//sy2,
        		null);
    }
    
    public void drawPixmap(Pixmap pixmap, double x, double y, Camera cam) {
    	BufferedImage img = ((PCPixmap) pixmap).bitmap;
    	g.drawImage(img,
    			(int)((x-cam.viewX)/cam.viewW*bufferWidth),
    			(int)((y-cam.viewY)/cam.viewH*bufferHeight),
    			(int)((img.getWidth())/cam.viewW*bufferWidth),
    			(int)((img.getHeight())/cam.viewH*bufferHeight),
    			null);
    	img = null;
    }

    /////////////////////////////////////////////////
    
    public void drawPixel(int x, int y, int color) {
        g.setColor(new Color(color));
        g.drawRect(
        		x,
        		y,
        		1,
        		1
        		);
    }

    public void drawLine(int x, int y, int x2, int y2, int color) {
        g.setColor(new Color(color));
        g.drawLine(
        		x,
        		y,
        		x2,
        		y2
        		);
    }

    public void drawRect(int x, int y, int width, int height, int color) {
        g.setColor(new Color(color));
        g.fillRect(
        		x,
        		y,
        		width,
        		height
        		);
    }

    public void drawRect(int x, int y, int width, int height, int c_r, int c_g, int c_b, int c_alpha) {
        g.setColor(new Color(c_r,c_g,c_b,c_alpha));
        g.fillRect(
        		x,
        		y,
        		width,
        		height
        		);
    }

    public void drawRect2(int x0, int y0, int x1, int y1, int color) {
        g.setColor(new Color(color));
        g.fillRect(
        		x0,
        		y0,
        		x1-x0,
        		y1-y0
        		);
    }

    public void drawPixmap(Pixmap pixmap, int x, int y, int srcX, int srcY,
            int srcWidth, int srcHeight) {

        g.drawImage(((PCPixmap) pixmap).bitmap,
        		x,//dx1,
        		y,//dy1,
        		x + srcWidth - 1,//dx2,
        		y + srcHeight - 1,//dy2,
        		srcX,//sx1,
        		srcY,//sy1,
        		srcX + srcWidth - 1,//sx2,
        		srcY + srcHeight - 1,//sy2,
        		null);
    }
    
    public void drawPixmap(Pixmap pixmap, int x, int y,
            int dstWidth, int dstHeight){
    	
    	g.drawImage(((PCPixmap) pixmap).bitmap,
    			x,
    			y,
    			dstWidth,
    			dstHeight,
    			null);
    }
    
    public void drawPixmap(Pixmap pixmap, int x, int y, int srcX, int srcY,
            int srcWidth, int srcHeight, int dstWidth, int dstHeight) {

        g.drawImage(((PCPixmap) pixmap).bitmap,
        		x,//dx1,
        		y,//dy1,
        		x + dstWidth - 1,//dx2,
        		y + dstHeight - 1,//dy2,
        		srcX,//sx1,
        		srcY,//sy1,
        		srcX + srcWidth - 1,//sx2,
        		srcY + srcHeight - 1,//sy2,
        		null);
    }
    
    public void drawPixmap(Pixmap pixmap, int x, int y) {
    	g.drawImage(((PCPixmap) pixmap).bitmap,
    			x,
    			y,
    			null);
    }
    
    //////////////////////////////////////////////////////
    
    public void drawText(String text, int x, int y, int color){
    	g.setColor(new Color(color));
    	g.setFont(font);
    	g.drawString(text, x, y);	
    }
    
    public void drawText(String text, double x, double y, int color, Camera cam){
    	g.setColor(new Color(color));
    	g.setFont(font);
    	g.drawString(text, (int)((x-cam.viewX)/cam.viewW*bufferWidth), (int)((y-cam.viewY)/cam.viewH*bufferHeight));	
    }
    
    public void drawText(String text, int x, int y, int maxW, int maxH, int color){
    	Rectangle oldClip = g.getClipBounds();
    	Rectangle newClip = new Rectangle(x,y-maxH,maxW,maxH);
    	g.setClip(newClip);
    	g.setColor(new Color(color));
    	g.setFont(font);
    	g.drawString(text, x, y);
    	g.setClip(oldClip);
    	newClip = null;
    }    
    
    public void drawText(String text, int x, int y, int maxW, int maxH, int color, Camera cam){
    	int xNew = (int)((x-cam.viewX)/cam.viewW*bufferWidth);
    	int yNew = (int)((y-cam.viewY)/cam.viewH*bufferHeight);
    	int wNew = (int)(maxW/cam.viewW*bufferWidth);
    	int hNew = (int)(maxH/cam.viewH*bufferHeight);
    	
    	Rectangle oldClip = g.getClipBounds();
    	Rectangle newClip = new Rectangle(xNew,yNew-hNew,wNew,hNew);
    	g.setClip(newClip);
    	g.setColor(new Color(color));
    	g.setFont(Font.getFont(Font.SANS_SERIF));
    	g.drawString(text, xNew, yNew);
    	g.setClip(oldClip);
    	newClip = null;
    	
    }
    
    public int getWidth() {
        return bufferWidth;
    }

    public int getHeight() {
        return bufferHeight;
    }

	@Override
	public void setClipBounds(int x1, int y1, int x2, int y2) {
		g.setClip(
				x1,
				y1,
				x2-x1+1,
				y2-y1+1
				);
	}

	@Override
	public void resetClipBounds() {
		g.setClip(null);
	}
}

