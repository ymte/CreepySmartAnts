package framework;

public abstract class ToggleButton extends Button{
	
	public Pixmap toggledPix;
	public boolean isToggled = false;
	
	public int srcXt;
	public int srcYt;
	public int srcWt;
	public int srcHt;
	
	private ToggleButton[] sharedToggles = null;;
	
	public ToggleButton(Pixmap pix, Pixmap toggledPix, int x, int y){
		super(pix, x, y);
		this.toggledPix = toggledPix;
	}
	
	@Override
	public void paint(Graphics g){
		if(isToggled){
			if(w < 0 || h < 0){
				if(srcX < 0 || srcY < 0 || srcW < 0 || srcH < 0){
					g.drawPixmap(toggledPix, x, y);
				}else{
					g.drawPixmap(toggledPix, x, y, srcX, srcY, srcW, srcH);
				}
			}else{
				if(srcX < 0 || srcY < 0 || srcW < 0 || srcH < 0){
					g.drawPixmap(toggledPix, x, y, w, h);	
				}else{
					g.drawPixmap(toggledPix, x, y, srcX, srcY, srcW, srcH, w, h);	
				}		
			}
		}
		else{

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
	}

	public void pressButton(int x, int y){
		if(inBounds(x,y)){
			isToggled = !isToggled;
			if(isToggled && sharedToggles != null){
				for(int i = 0; i<sharedToggles.length; i++){
					sharedToggles[i].toggleOff();
				}
				isToggled=true; //We just turned it to 'false', because it was 'true'. Undo that!
			}
			onToggle(isToggled);
		}
	}
	
	public void toggleOff(){
		isToggled=false;
	}
	
	public abstract void onToggle(boolean isToggled);
	
	public void registerSharedToggles(ToggleButton[] sharedToggles){
		this.sharedToggles = sharedToggles;
	}
	
}
