package MallVisualiser;

import framework.Screen;
import framework.impl.PCGame;

public class MallVisualiser extends PCGame {

	public MallVisualiser(String title) {
		super(title);
	}

	@Override
	public Screen getStartScreen() {
		
		return new LoadingScreen(this);
	}

	
}
