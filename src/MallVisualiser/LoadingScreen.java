package MallVisualiser;

import framework.Game;
import framework.Graphics;
import framework.Graphics.PixmapFormat;
import framework.Screen;


public class LoadingScreen extends Screen {
    public LoadingScreen(Game game) {
        super(game);
    }

    public void update(float deltaTime) {
        Graphics g = game.getGraphics();
        
        //Menu
        Assets.menuBackground = g.newPixmap("menuBackground.jpg", PixmapFormat.RGB565);
        Assets.menuButtonEditorMode = g.newPixmap("EditorMode.png", PixmapFormat.ARGB4444);
        Assets.menuButtonVisualiserMode = g.newPixmap("VisualiserMode.png", PixmapFormat.ARGB4444);
        Assets.menuButtonGCMode = g.newPixmap("Button_GrandChallengeMode.png", PixmapFormat.ARGB4444);
        
        //Editor-mode
        Assets.editorFloor = g.newPixmap("Pixel_White.png", PixmapFormat.ARGB4444);
        Assets.editorWall = g.newPixmap("Pixel_Gray.png", PixmapFormat.ARGB4444);
        Assets.editorBackground = g.newPixmap("EditorBackground.png", PixmapFormat.RGB565);
        Assets.editorBuildIcon = g.newPixmap("EditorBuildIcon.png", PixmapFormat.RGB565);
        Assets.editorBuildIcon_toggled = g.newPixmap("EditorBuildIcon_toggled.png", PixmapFormat.RGB565);
        Assets.editorDestroyIcon = g.newPixmap("EditorDestroyIcon.png", PixmapFormat.RGB565);
        Assets.editorDestroyIcon_toggled = g.newPixmap("EditorDestroyIcon_toggled.png", PixmapFormat.RGB565);
        Assets.editorNewMapIcon = g.newPixmap("EditorNewMapIcon.png", PixmapFormat.RGB565);
        Assets.editorResizeMapIcon = g.newPixmap("EditorResizeMapIcon.png", PixmapFormat.RGB565);
        Assets.editorSaveMapIcon = g.newPixmap("EditorSaveMapIcon.png", PixmapFormat.RGB565);
        Assets.editorLoadMapIcon = g.newPixmap("EditorLoadMapIcon.png", PixmapFormat.RGB565);
        Assets.editorToMenuIcon = g.newPixmap("EditorToMenuIcon.png", PixmapFormat.RGB565);
        
        //Visualiser-mode
        Assets.visualiserRouteGround0 = g.newPixmap("VisualiserRouteGround.png", PixmapFormat.RGB565);
        Assets.visualiserRouteGround1 = g.rotatePixmap(Assets.visualiserRouteGround0, -Math.PI/2);
        Assets.visualiserRouteGround2 = g.rotatePixmap(Assets.visualiserRouteGround0, 2*Math.PI/2);
        Assets.visualiserRouteGround3 = g.rotatePixmap(Assets.visualiserRouteGround0, Math.PI/2);
        Assets.visualiserLoadWorldIcon = g.newPixmap("VisualiserLoadWorldIcon.png", PixmapFormat.RGB565);
        Assets.visualiserLoadRouteIcon = g.newPixmap("VisualiserLoadRouteIcon.png", PixmapFormat.RGB565);
        Assets.visualiserClearRouteIcon = g.newPixmap("VisualiserClearRouteIcon.png", PixmapFormat.RGB565);
        Assets.visualiserToMenuIcon = g.newPixmap("EditorToMenuIcon.png", PixmapFormat.RGB565);
        
        //Settings.load(game.getFileIO());
        game.setScreen(new MainMenuScreen(game));
    }
    
    public void present(float deltaTime) {

    }

    public void pause() {

    }

    public void resume() {

    }

    public void dispose() {

    }
}
