package MallVisualiser;

import MallVisualiser.Editor.EditorScreen;
import MallVisualiser.Visualiser.VisualiserScreen;
import framework.Button;
import framework.Game;
import framework.Graphics;
import framework.Pixmap;

public class MenuButton extends Button{
	
	private Game game;
	
	public MenuButton(ButtonType type, Game game, Pixmap pix, int x, int y){
		super(pix, x, y);
		this.type = type;
		this.game = game;
	}
	
	public static enum ButtonType {
		Editor,
		Visualiser,
		GrandChallenge,
		Quit}
	
	private ButtonType type;
	
	@Override
	public void paint(Graphics g){
		super.paint(g);
		if(type == ButtonType.GrandChallenge){
			g.drawRect(x, y, w, h, 80, 40, 40, 220);
		}
	}
	
	@Override
	public void pressButton(int x, int y) {
		if(inBounds(x,y)){
			if(type == ButtonType.Editor){
		        game.setScreen(new EditorScreen(game));
			}else if(type == ButtonType.Visualiser){
				game.setScreen(new VisualiserScreen(game));
			}else if(type == ButtonType.GrandChallenge){
				//Not in this version of the code ;)
			}else if(type == ButtonType.Quit){
				System.exit(0);
			}
		}
		
	}

	
}
