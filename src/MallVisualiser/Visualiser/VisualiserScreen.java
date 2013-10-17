package MallVisualiser.Visualiser;


import java.awt.event.MouseEvent;
import java.util.List;

import MallVisualiser.Assets;
import MallVisualiser.Editor.World;
import MallVisualiser.Editor.WorldIO;

import framework.Button;
import framework.Camera;
import framework.Game;
import framework.Graphics;
import framework.ToggleButton;
import framework.Input.TouchEvent;
import framework.Screen;


public class VisualiserScreen extends Screen {
	private ToggleButton[] toggleButtons;
	private Button[] buttons;
	private World world;
	private WorldIO worldIO;
	private Route route;
	private RouteIO routeIO;
	
	private int mouseX;
	private int mouseY;
	private boolean[] buttonDown = new boolean[4];
	
	private Camera cam;
	
    public VisualiserScreen(Game game) {
        super(game);
        this.cam = game.getCamera();
        world = World.newWorld(0,0);
        worldIO = new WorldIO(world,game.getFileIO());
        
        route = new Route(Assets.visualiserRouteGround0,
        		Assets.visualiserRouteGround1,
        		Assets.visualiserRouteGround2,
        		Assets.visualiserRouteGround3,
        		world);
        routeIO = new RouteIO(route,game.getFileIO());
        
        buttons = new Button[4];
        {
	        buttons[0] = new VisualiserButton(
	        		VisualiserButton.ButtonType.ClearRoute,
	        		game,
	        		Assets.visualiserClearRouteIcon,
	        		824+25,
	        		500,
	        		worldIO,
	        		routeIO
	        		);
	    	buttons[0].w = 50;
	    	buttons[0].h = 50;
        }
        {
	        buttons[1] = new VisualiserButton(
	        		VisualiserButton.ButtonType.LoadWorld,
	        		game,
	        		Assets.visualiserLoadWorldIcon,
	        		824+25,
	        		600,
	        		worldIO,
	        		routeIO
	        		);
	    	buttons[1].w = 50;
	    	buttons[1].h = 50;
        }
        {
	        buttons[2] = new VisualiserButton(
	        		VisualiserButton.ButtonType.LoadRoute,
	        		game,
	        		Assets.visualiserLoadRouteIcon,
	        		824+25+50+25,
	        		600,
	        		worldIO,
	        		routeIO
	        		);
	    	buttons[2].w = 50;
	    	buttons[2].h = 50;
        }
        {
	        buttons[3] = new VisualiserButton(
	        		VisualiserButton.ButtonType.toMenu,
	        		game,
	        		Assets.visualiserToMenuIcon,
	        		824+25,
	        		700,
	        		worldIO,
	        		routeIO
	        		);
	    	buttons[3].w = 50;
	    	buttons[3].h = 50;
        }
        toggleButtons = new ToggleButton[0];
        
    }   

    public void update(float deltaTime) {
    	Graphics g = game.getGraphics();
    	int bufferWidth = g.getWidth();
    	int bufferHeight = g.getHeight();
        List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
        game.getInput().getKeyEvents();       
        
        int len = touchEvents.size();
        for(int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);
            
            //Zooming:
        	if(event.type == TouchEvent.TOUCH_DOWN){
            	mouseX = event.x;
            	mouseY = event.y;
            	buttonDown[event.button]=true;        		
        	}
        	if(event.type == TouchEvent.TOUCH_UP){
            	mouseX = event.x;
            	mouseY = event.y;
            	buttonDown[event.button]=false;    
            	world.deselectTile();      		
        	}
            
            if(event.type == TouchEvent.TOUCH_DRAGGED) {
            	if(buttonDown[MouseEvent.BUTTON3]){
	            	cam.moveCameraWith(mouseX-event.x,mouseY-event.y);
	            	mouseX = event.x;
	            	mouseY = event.y;
            	}
            	if(buttonDown[MouseEvent.BUTTON2]){
	            	cam.changeCameraHeightWith(event.y-mouseY);
	            	mouseX = event.x;
	            	mouseY = event.y;
            	}
            	
            }
            
            //Press buttons:
            if(event.type == TouchEvent.TOUCH_UP && event.button == MouseEvent.BUTTON1){
            	for(int j = 0; j<buttons.length; j++){
            		buttons[j].pressButton(
            				event.x,
            				event.y
            				);
            	}
            	for(int j = 0; j<toggleButtons.length; j++){
            		toggleButtons[j].pressButton(
            				event.x,
            				event.y
            				);
            	}
            }
            
            //Building:
            if(event.type == TouchEvent.TOUCH_DRAGGED || event.type == TouchEvent.TOUCH_DOWN) {
            	if(buttonDown[MouseEvent.BUTTON1]){
            		world.selectTile(
            				(int)(event.x*cam.viewW/bufferWidth + cam.viewX),
            				(int)(event.y*cam.viewH/bufferHeight + cam.viewY)
            				);
            	}            	
            }
            
        }
    }

    public void present(float deltaTime) {
        Graphics g = game.getGraphics();
        g.clear(0xff000000);
        
        //World:
        g.setClipBounds(0,0,824,800);
        world.paint(g, cam);
        route.paint(g, cam);
        world.paintSelectedIndex(g, cam);
        g.resetClipBounds();
        
        //UI:
        g.drawPixmap(Assets.editorBackground, 824, 0);
        for(int i = 0; i<toggleButtons.length; i++){
        	toggleButtons[i].paint(g);
        }
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

