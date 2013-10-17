package MallVisualiser.AntSimulator;

import java.util.LinkedList;
import java.util.List;

import MallVisualiser.Editor.World;

public class AntManager {
	public List<Ant> ants;
	private int ticksPerCycle;
	private int ticks;
	private World world;
	private int numAnts;
	
	public AntManager(World world, int fps, int numAnts) {
		this.world = world;
		ticksPerCycle = 60 / fps;
		this.numAnts = numAnts;
		reset();
	}
	
	public void reset() {
		ants = new LinkedList<Ant>();
		for(int i = 0; i < numAnts; i++) {
			Node start = world.start;
			Node end = world.end;
			ants.add(new Ant(start, end, 1, 1));
		}
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
