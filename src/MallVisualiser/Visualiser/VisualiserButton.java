package MallVisualiser.Visualiser;

import java.awt.Frame;
import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;

import MallVisualiser.MainMenuScreen;
import MallVisualiser.Editor.WorldIO;

import framework.Game;
import framework.Pixmap;
import framework.Button;

public class VisualiserButton extends Button{
	
	private final String[] myFileLocations = new String[]{ //TODO: Hard-coded file locations. Remove eventually.
			"C:\\Users\\Victor\\Documents\\MATLAB\\Computational Intelligence\\Assignment3",
			"C:\\Users\\Kevin\\Dropbox\\TUDelft\\TI2735 - Computational Intelligence\\Assignment 3",
			"C:\\Users\\Kevin van As\\Dropbox\\TUDelft\\TI2735 - Computational Intelligence\\Assignment 3"};
	
	private Game game;
	private WorldIO worldIO;
	private RouteIO routeIO;
	
	private JFileChooser world_fc;
	private JFileChooser route_fc;
	
	public VisualiserButton(ButtonType type, Game game, Pixmap pix, int x, int y, WorldIO worldIO, RouteIO routeIO){
		super(pix, x, y);
		this.type = type;
		this.game = game;
		this.worldIO = worldIO;
		this.routeIO = routeIO;
		
		world_fc = new JFileChooser();
		world_fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		world_fc.setDialogTitle("Select a world file");
		
		route_fc = new JFileChooser();
		route_fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		route_fc.setDialogTitle("Select a route file");

		File myMainDir;
		for(int i = 0; i<myFileLocations.length; i++){
			myMainDir = new File(myFileLocations[i]);
			if(myMainDir.exists()){
				world_fc.setCurrentDirectory(myMainDir);
				route_fc.setCurrentDirectory(myMainDir);
				break;
			}
		}
	}
	
	public static enum ButtonType {
		ClearRoute,
		LoadWorld,
		LoadRoute,
		toMenu
	}
	
	private ButtonType type;

	@Override
	public void pressButton(int x, int y) {
		if(inBounds(x,y)){
			//New:
			if(this.type == ButtonType.ClearRoute){
				routeIO.resetRoute();
			}
			
			//Load Worldmap:
			if(this.type == ButtonType.LoadWorld){	
				int returnVal = world_fc.showOpenDialog(null);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					worldIO.load(world_fc.getSelectedFile());
		        }
				
			}
			
			//Load Route:
			if(this.type == ButtonType.LoadRoute){
				int returnVal = route_fc.showOpenDialog(null);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					routeIO.load(route_fc.getSelectedFile());
		        }
				
			}
			
			//Back-to-menu:
			if(this.type == ButtonType.toMenu){
				toMenu();
			}			
			
		}
	}
	
	public boolean save(){
		JFileChooser fc = new JFileChooser();
		int returnVal = fc.showSaveDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
            worldIO.save(fc.getSelectedFile());
            return true;
        }else{
        	return false;
        }
	}
	
	public void toMenu(){
		game.setScreen(new MainMenuScreen(game));
	}
	
}
