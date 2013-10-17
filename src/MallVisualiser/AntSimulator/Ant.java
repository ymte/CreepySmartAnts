package MallVisualiser.AntSimulator;

import MallVisualiser.Editor.World;

public class Ant {
	public int x;
	public int y;
	public World world;
	
	public Ant(World world, int x, int y) {
		this.x = x;
		this.y = y;
		this.world = world;
	}
	
	public void reset() {
		x = world.startTile_x;
		y = world.startTile_y;
	}
	
	public void tick() {
			 if (world.isAccessible(x+1, y) && Math.random() > 0.5)
			x += 1;
		else if (world.isAccessible(x-1, y) && Math.random() > 0.5)
			x -= 1;
		else if (world.isAccessible(x, y+1) && Math.random() > 0.5)
			y += 1;
		else if (world.isAccessible(x, y-1) && Math.random() > 0.5)
			y -= 1;
	}
}
