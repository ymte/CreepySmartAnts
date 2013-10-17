package MallVisualiser.AntSimulator;

import java.util.ArrayList;

/*
 * Tom Peeters - 4176510
 * Rutger van den Berg - 4060156
 * Ymte Jan Broekhuizen - 4246586
 * Arne Hutter - 4108566
 */
public class Ant {

	ArrayList<Node> path;
	public Node loc, prevLoc;
	int steps;
	double a, b;

	public Ant(Node start, Node end, double a, double b) {
		path = new ArrayList<>();
		path.add(start);
		loc = start;
		prevLoc = start;
		steps = 0;
		this.a = a;
		this.b = b;
	}

	public void tick() {
		ArrayList<Node> locNodes = loc.getNodes();
		locNodes.remove(prevLoc);
		ArrayList<Double> x = new ArrayList<>();
		Double sum = 0.0;
		for (int i = 0; i < locNodes.size(); i++) {
			x.add(a * loc.getPheremone(locNodes.get(i)) + b
					* loc.getLength(locNodes.get(i)));
			sum += x.get(i);
		}
		ArrayList<Double> p = new ArrayList<>();
		for (int i = 0; i < locNodes.size(); i++) {
			p.add(x.get(i) / sum);
		}
		Double rand = Math.random();
		for (int i = 0; i < locNodes.size(); i++) {
			rand -= p.get(i);
			if (rand < 0) {
				prevLoc = loc;
				loc = locNodes.get(i);
				break;
			}
		}
		path.add(loc);
		steps++;
	}

	public ArrayList<Node> getPath() {
		return path;
	}
}
