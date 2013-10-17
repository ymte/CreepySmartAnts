package MallVisualiser.AntSimulator;

import java.util.LinkedList;
import java.util.List;

import MallVisualiser.Editor.World;

public class AntManager {
	public List<Ant> ants;
	private int ticksPerCycle;
	private int ticks;
	
	public AntManager(World world, int fps, int numAnts) {
		ticksPerCycle = 60 / fps;
		ants = new LinkedList<Ant>();
		for(int i = 0; i < 10; i++)
			ants.add(new Ant(world, world.startTile_x, world.startTile_y));
	}
	
	public void reset() {		
		for(Ant a: ants)
			a.reset();
	}
	
	public void update() {
		if (ticks++ > ticksPerCycle) {
			tick();
			ticks = 0;
		}
	}
	
	public void tick() {
		for(Ant a: ants)
			a.tick();
	}
}
