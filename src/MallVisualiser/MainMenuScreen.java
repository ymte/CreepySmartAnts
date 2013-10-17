package MallVisualiser;


import java.util.List;

import framework.Button;
import framework.Game;
import framework.Graphics;
import framework.Input.TouchEvent;
import framework.Screen;


public class MainMenuScreen extends Screen {
	private Button[] buttons;
	
    public MainMenuScreen(Game game) {
        super(game);
        buttons = new Button[4];
        buttons[0] = new MenuButton(
        		MenuButton.ButtonType.Editor,
        		game,
        		Assets.menuButtonEditorMode,
        		100,
        		100
        		);
        buttons[0].w = 200;
        buttons[0].h = 147;
        buttons[1] = new MenuButton(
        		MenuButton.ButtonType.Visualiser,
        		game,
        		Assets.menuButtonVisualiserMode,
        		325,
        		100
        		);
        buttons[1].w = 200;
        buttons[1].h = 147;
        buttons[2] = new MenuButton(
        		MenuButton.ButtonType.GrandChallenge,
        		game,
        		Assets.menuButtonGCMode,
        		100,
        		300
        		);
        buttons[2].w = 200;
        buttons[2].h = 147;

        buttons[3] = new MenuButton(
        		MenuButton.ButtonType.Quit,
        		game,
        		Assets.editorToMenuIcon,
        		1024-150,
        		800-150
        		);
        buttons[3].w = 100;
        buttons[3].h = 100;
    }   

    public void update(float deltaTime) {
        List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
        game.getInput().getKeyEvents();       
        
        int len = touchEvents.size();
        for(int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);
            if(event.type == TouchEvent.TOUCH_UP) {
                for(int j = 0; j<buttons.length; j++){
                	buttons[j].pressButton(event.x, event.y);
                }
            }
        }
    }

    public void present(float deltaTime) {
        Graphics g = game.getGraphics();
        
        g.drawPixmap(Assets.menuBackground, 0, 0);
        
        for(int i = 0; i<buttons.length; i++){
        	buttons[i].paint(g);
        }
        
    }

    public void pause() {        
       // Settings.save(game.getFileIO());
    }

    public void resume() {

    }

    public void dispose() {

    }
}

