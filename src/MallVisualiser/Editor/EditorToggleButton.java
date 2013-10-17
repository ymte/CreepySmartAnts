package MallVisualiser.Editor;

import framework.Game;
import framework.Pixmap;
import framework.ToggleButton;

public class EditorToggleButton extends ToggleButton{
	
	private Game game;
	
	public EditorToggleButton(ButtonType type, Game game, Pixmap pix, Pixmap toggledPix, int x, int y){
		super(pix, toggledPix, x, y);
		this.type = type;
		this.game = game;
	}
	
	public static enum ButtonType {
		Buildmode,
		Destroymode
	}
	
	private ButtonType type;
	
	@Override
	public void onToggle(boolean isToggled) {
		if(type == ButtonType.Buildmode){
	        
		}else if(type == ButtonType.Destroymode){
			
		}
	}
	
}
