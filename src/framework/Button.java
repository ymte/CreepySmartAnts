package framework;


public abstract class Button {
	
	public Pixmap pix;
	public int srcX = -1;
	public int srcY = -1;
	public int srcW = -1;
	public int srcH = -1;
	
	public int x;
	public int y;
	public int w = -1;
	public int h = -1;
		
	public Button(Pixmap pix, int x, int y){
		this.pix = pix;
		this.x = x;
		this.y = y;
	}
	
	public boolean inBounds(int x, int y){
		 if(x > this.x && x < this.x + w - 1 && 
		           y > this.y && y < this.y + h - 1) 
			 return true;
		 else
		     return false;
	}
	
	public void paint(Graphics g){
		if(w < 0 || h < 0){
			if(srcX < 0 || srcY < 0 || srcW < 0 || srcH < 0){
				g.drawPixmap(pix, x, y);
			}else{
				g.drawPixmap(pix, x, y, srcX, srcY, srcW, srcH);
			}
		}else{
			if(srcX < 0 || srcY < 0 || srcW < 0 || srcH < 0){
				g.drawPixmap(pix, x, y, w, h);	
			}else{
				g.drawPixmap(pix, x, y, srcX, srcY, srcW, srcH, w, h);	
			}		
		}
	}
	
	public abstract void pressButton(int x, int y);
	
}
