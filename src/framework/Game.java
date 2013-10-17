package framework;

public interface Game {
    public Input getInput();

    public FileIO getFileIO();

    public framework.Graphics getGraphics();

    public void setScreen(Screen screen);

    public Screen getCurrentScreen();

    public Screen getStartScreen();
    
    public Camera getCamera();
    
    public Object getContainer();
}