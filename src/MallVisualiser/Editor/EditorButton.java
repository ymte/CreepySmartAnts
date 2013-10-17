package MallVisualiser.Editor;

import java.awt.Frame;
import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;

import MallVisualiser.MainMenuScreen;

import framework.Game;
import framework.Pixmap;
import framework.Button;

public class EditorButton extends Button{

	private final String[] myFileLocations = new String[]{ //TODO: Hard-coded file locations. Remove eventually.
			"C:\\Users\\Victor\\Documents\\MATLAB\\Computational Intelligence\\Assignment3",
			"C:\\Users\\Kevin\\Dropbox\\TUDelft\\TI2735 - Computational Intelligence\\Assignment 3",
			"C:\\Users\\Kevin van As\\Dropbox\\TUDelft\\TI2735 - Computational Intelligence\\Assignment 3"};
	
	private Game game;
	private WorldIO worldIO;
	private NewMapDialog newMapDialog;
	private ResizeMapDialog resizeMapDialog;
	private BackToMenuDialog toMenuDialog;
	
	private JFileChooser world_fc;
	
	public EditorButton(ButtonType type, Game game, Pixmap pix, int x, int y, WorldIO worldIO){
		super(pix, x, y);
		this.type = type;
		this.game = game;
		this.worldIO = worldIO;
		if(type == ButtonType.NewMap){
			newMapDialog = new NewMapDialog((Frame)game.getContainer(), worldIO);
		}
		if(type == ButtonType.ResizeMap){
			resizeMapDialog = new ResizeMapDialog((Frame)game.getContainer(), worldIO);
		}
		if(type == ButtonType.toMenu){
			toMenuDialog = new BackToMenuDialog((Frame)game.getContainer(), this);
		}
		
		world_fc = new JFileChooser();
		world_fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		world_fc.setDialogTitle("Select a world file");

		File myMainDir;
		for(int i = 0; i<myFileLocations.length; i++){
			myMainDir = new File(myFileLocations[i]);
			if(myMainDir.exists()){
				world_fc.setCurrentDirectory(myMainDir);
				break;
			}
		}
	}
	
	public static enum ButtonType {
		NewMap,
		ResizeMap,
		SaveMap,
		LoadMap,
		toMenu
	}
	
	private ButtonType type;

	@Override
	public void pressButton(int x, int y) {
		if(inBounds(x,y)){
			//New:
			if(this.type == ButtonType.NewMap){
				newMapDialog.setVisible(true);
			}
			
			//Resize:
			if(this.type == ButtonType.ResizeMap){
				resizeMapDialog.setVisible(true);				
			}
			
			//Save:
			if(this.type == ButtonType.SaveMap){
				this.save();
			}
			
			//Load:
			if(this.type == ButtonType.LoadMap){
				int returnVal = world_fc.showOpenDialog(null);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					worldIO.load(world_fc.getSelectedFile());
		        }
				
			}
			
			//Back-to-menu:
			if(this.type == ButtonType.toMenu){
				toMenuDialog.setVisible(true);
			}			
			
		}
	}
	
	public boolean save(){
		int returnVal = world_fc.showSaveDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
            worldIO.save(world_fc.getSelectedFile());
            return true;
        }else{
        	return false;
        }
	}
	
	public void toMenu(){
		game.setScreen(new MainMenuScreen(game));
	}
	
}
